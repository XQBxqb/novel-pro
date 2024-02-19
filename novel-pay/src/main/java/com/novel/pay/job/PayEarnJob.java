package com.novel.pay.job;

import com.novel.core.entity.ChapterEarning;
import com.novel.pay.dao.ChapterEarningMapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PayEarnJob {
    @Autowired
    private ChapterEarningMapper chapterEarningMapper;
    @XxlJob("payEarnJob")
    @Transactional(rollbackFor = Exception.class)
    public void execute(){
        log.info(LocalDateTime.now()+ " : begin xxl job payEarnJob " +this.getClass().getName());
        List<ChapterEarning> chapterEarnings = null;
        try {
            chapterEarnings=chapterEarningMapper.queryEarning();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        chapterEarnings.stream().forEach(t-> log.info(t.getAuthorId()+" has earn coins: "+t.getCoins()+LocalDateTime.now()));
        //... job to Transfer money ...
        List<Long> idList = chapterEarnings.stream()
                                            .map(t -> t.getAuthorId())
                                            .collect(Collectors.toList());
        chapterEarningMapper.updateEarning(idList);
        log.info(LocalDateTime.now()+"finish payEarnJob");
    };
}
