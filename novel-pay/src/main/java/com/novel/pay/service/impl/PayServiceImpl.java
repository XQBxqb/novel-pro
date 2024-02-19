package com.novel.pay.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.novel.api.exception.BusinessException;
import com.novel.core.consts.ElasticConsts;
import com.novel.core.consts.RedisConsts;
import com.novel.core.document.CouponES;
import com.novel.core.dto.ReqCouponDto;
import com.novel.core.entity.*;
import com.novel.core.enums.*;
import com.novel.core.res.RestRes;
import com.novel.pay.dao.*;
import com.novel.pay.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayServiceImpl implements PayService {
    private final Redisson redisson;


    private final UserOwnCouponMapper userOwnCouponMapper;

    private final ChapterValueMapper chapterValueMapper;

    private final UserPayChapterMapper userPayChapterMapper;

    private final ChapterEarningMapper chapterEarningMapper;

    private final ChapterTradeLogMapper chapterTradeLogMapper;

    private final BookInfoMapper bookInfoMapper;

    private final UserCoinMapper userCoinMapper;

    @Autowired
    @Qualifier("getElasticSearchClient")
    private ElasticsearchClient elasticsearchClient;

    @Override
    public RestRes<Void> releaseCouponDocument(ReqCouponDto couponDto) {

        RLock lock = redisson.getLock(RedisConsts.REDIS_PAY_COUPON_LOCK);
        lock.lock();
        try {
            RAtomicLong atomicLong = redisson.getAtomicLong(RedisConsts.REDIS_PAY_COUPON_ES_ID);
            long couponId = atomicLong.getAndAdd(1L);
            CouponES couponES = new CouponES();
            couponES.setId(new Long(couponId).intValue());
            BeanUtils.copyProperties(couponDto, couponES);
            try {
                elasticsearchClient.index(i -> i.index(ElasticConsts.INDEX_COUPON_NAME)
                                                .id(couponId + "")
                                                .document(couponES));
            } catch (IOException e) {
                throw new BusinessException(ErrorStatusEnums.RES_SYSTEM_ERROR);
            }
        } finally {
            lock.unlock();
        }
        return RestRes.ok();
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Void> getCoupon(Long couponId, Long userId) {
        RLock lock = redisson.getLock(RedisConsts.REDIS_PAY_COUPON_GET_PREFIX + couponId);
        lock.lock();
        try {
            CouponES couponES = getCouponES(couponId);
            if (couponES.getLimitNums() == 0)
                return RestRes.errorEnum(ErrorStatusEnums.RES_PAY_COUPON_NUM_LIMIT);
            if (couponES.getLimitNums() != -1)
                couponES.setLimitNums(couponES.getLimitNums() - 1);
            log.info("coupon:"+couponId+" after this trade last Num:"+couponES.getLimitNums());
            elasticsearchClient.update(u -> u.index(ElasticConsts.INDEX_COUPON_NAME)
                                             .id(couponId + "")
                                             .doc(couponES), CouponES.class);

            UserOwnCoupon userOwnCoupon = new UserOwnCoupon(userId, couponId, couponES.getEndTime(), 0);
            userOwnCouponMapper.insert(userOwnCoupon);
        } finally {
            lock.unlock();
        }
        return RestRes.ok();
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Void> shoppingChapterId(Long userId, Long chapterId, Long couponId) {
        RestRes<Void> res = null;
        RLock lock = redisson.getLock(RedisConsts.REDIS_PAY_COUPON_SHOPPING_PREFIX);
        lock.lock();

        ChapterTradeLog tradeLog = null;
        try {
            ChapterValue chapterValue = chapterValueMapper.selectById(chapterId);
            List<Object> objects = bookInfoMapper.queryAuthorIdByChapterID(chapterId);
            BigInteger authorIdBig =  (BigInteger)objects.get(0);
            Long authorId = authorIdBig.longValue();
            Integer coinsValue = chapterValue.getCoinsValue();
            if (couponId != -1) {
                res = payOfCoupon(couponId, userId, chapterValue, authorId, coinsValue, tradeLog);
            } else {
                res = dtoChapterPayLog(couponId, userId, authorId, coinsValue, chapterId, tradeLog);
            }
        } catch (IOException e) {
            log.error("PayServiceImpl shoppingChapterId api error");
            tradeLog.setTradeType(TradeLogTypeEnums.ERROR_STOP_TRADE.value);
            throw new BusinessException(ErrorStatusEnums.RES_SYSTEM_ERROR);
        } finally {
            lock.unlock();
        }
        return res;
    }

    private RestRes<Void> payOfCoupon(Long couponId, Long userId, ChapterValue chapterValue, Long authorId, Integer coinsValue, ChapterTradeLog tradeLog) throws IOException {
        Integer isUsedCoupon;
        LocalDateTime couponEndTime;

        Integer exactCoinsCost = coinsValue;
        Long chapterId = chapterValue.getChapterId();

        List<UserOwnCoupon> userOwnCoupons = userOwnCouponMapper.getOwnCoupon(userId,couponId);
        UserOwnCoupon userOwnCoupon= userOwnCoupons.get(0);
        couponEndTime = userOwnCoupon.getEndTime();
        isUsedCoupon = userOwnCoupon.getIsUsed();

        CouponES couponES = getCouponES(couponId);
        CouponES.Restrict restrict = couponES.getRestrict();

        conditionOfUserCoupon(couponId, chapterValue, isUsedCoupon, couponEndTime, coinsValue, restrict);

        if (ESCouponTypeNums.DIRECT_REDUCE.value.equals((int) restrict.getType())) {
            exactCoinsCost = Math.max(coinsValue - restrict.getDiscount().intValue(), 0);
        } else {
            exactCoinsCost = (int) (coinsValue * restrict.getDiscount());
        }

        int res = userOwnCouponMapper.updateOwnCoupon(userId, couponId);
        return dtoChapterPayLog(couponId, userId, authorId, exactCoinsCost, chapterId, tradeLog);
    }

    private RestRes<Void> dtoChapterPayLog(Long couponId, Long userId, Long authorId, Integer exactCoinsCost, Long chapterId, ChapterTradeLog tradeLog) {
        tradeLog = ChapterTradeLog.builder()
                                  .tradeType(TradeLogTypeEnums.SUCCESS_TRADE.value)
                                  .chapterId(chapterId)
                                  .userId(userId)
                                  .couponId(couponId)
                                  .tradeTime(LocalDateTime.now())
                                  .exactCoins(exactCoinsCost)
                                  .isRefund(YesOrNoEnum.No.value)
                                  .build();
        UserPayChapter userPayChapter = UserPayChapter.builder()
                                                      .chapterId(chapterId)
                                                      .exactCoins(exactCoinsCost)
                                                      .userId(userId)
                                                      .isRead(YesOrNoEnum.No.value)
                                                      .tradeTime(LocalDateTime.now())
                                                      .build();
        ChapterEarning chapterEarning = ChapterEarning.builder()
                                                      .chapterId(chapterId)
                                                      .authorId(authorId)
                                                      .coins(exactCoinsCost)
                                                      .isEarn(YesOrNoEnum.No.value)
                                                      .tradeTime(LocalDateTime.now())
                                                      .isRefund(YesOrNoEnum.No.value)
                                                      .build();

        chapterTradeLogMapper.insert(tradeLog);
        userCoinMapper.updateCoin(-exactCoinsCost,userId);
        userPayChapterMapper.insert(userPayChapter);
        chapterEarningMapper.insert(chapterEarning);
        return RestRes.ok();
    }


    private void conditionOfUserCoupon(Long couponId, ChapterValue chapterValue, Integer isUsedCoupon, LocalDateTime couponEndTime, Integer coinsValue, CouponES.Restrict restrict) {
        if (couponId != -1 && chapterValue.getIsAllowedCoupon()
                                          .equals(YesOrNoEnum.No.value))
            throw new BusinessException(ErrorStatusEnums.RES_PAY_COUPON_CHAPTER_NOT_ALLOW_REDUCE);
        if (couponEndTime.isBefore(LocalDateTime.now()))
            throw new BusinessException(ErrorStatusEnums.RES_PAY_COUPON_USER_COUPON_AFTER_VALID);
        if (isUsedCoupon.equals(YesOrNoEnum.Yes.value))
            throw new BusinessException(ErrorStatusEnums.RES_PAY_COUPON_USER_COUPON_HAD_USER);
        if (restrict.getMinCost()
                    .equals(coinsValue.doubleValue()))
            throw new BusinessException(ErrorStatusEnums.RES_PAY_COUPON_SMALL_THAN_MIN_COST_OF_COIN);
    }

    private CouponES getCouponES(Long couponId) throws IOException {
        SearchResponse<CouponES> search = null;
        search = elasticsearchClient.search(s -> s.index(ElasticConsts.INDEX_COUPON_NAME)
                                                  .query(q -> q.ids(i -> i.values(Arrays.asList(couponId + ""))))
                , CouponES.class);
        CouponES couponES = search.hits()
                                  .hits()
                                  .stream()
                                  .map(h -> h.source())
                                  .collect(Collectors.toList())
                                  .get(CollectionListIndexEnum.FIRST.index);
        return couponES;
    }
}
