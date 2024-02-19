package com.novel.book.job;

import com.novel.book.dao.BookElasticMapper;
import com.novel.book.dao.BookInfoMapper;
import com.novel.core.document.BookElasticES;
import com.novel.core.entity.BookInfo;
import com.xxl.job.core.handler.annotation.XxlJob;
import jodd.util.ObjectUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class BookSyncJob {
    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Autowired
    private BookElasticMapper bookElasticMapper;
    @XxlJob("bookSync")
    @SneakyThrows
    public void bookSync(){
        try {
            log.info(LocalDateTime.now()+ " : begin xxl job bookSync " +this.getClass().getName());

            List<BookInfo> books = bookInfoMapper.getAll();
            List<BookElasticES> bookElasticES = bookElasticMapper.searchAllBook();

            List<BookInfo> bookInfos = books.stream()
                                          .sorted(Comparator.comparing(BookInfo::getId))
                                          .collect(Collectors.toList());
            List<BookElasticES> bookElasticESS = bookElasticES.stream()
                                                        .sorted(Comparator.comparing(BookElasticES::getId)).collect(Collectors.toList());
            AtomicInteger atomicInteger = new AtomicInteger(0);
            bookInfos.stream().forEach(t->{
                BookElasticES book = new BookElasticES();
                BeanUtils.copyProperties(t,book);

                // bookElastic中所有文档已经比较完毕，把剩余的数据库数据插入到搜索引擎
                int index = atomicInteger.get();
                String idStr = t.getId()+"";

                if(index == bookElasticESS.size()){
                    log.info("insert "+t.getId()+" "+Thread.currentThread().getStackTrace()[1].getLineNumber());
                    bookElasticMapper.updateBook(idStr,book);
                    return;
                }
                BookElasticES elasticES = bookElasticESS.get(index);
                log.info("trun "+t.getId() + " check index "+index+" es "+elasticES.getId());
                // bookElastic当前指向的文档id如果大于bookInfo当前指针指向的id，就说明bookElastic中没有改文档，直接插入该文档
                if(ObjectUtils.compare(t.getId(),elasticES.getId())<0){
                    log.info("insert "+t.getId()+" "+Thread.currentThread().getStackTrace()[1].getLineNumber());
                    bookElasticMapper.updateBook(idStr,book);
                    return;
                }
                // bookElastic指向和bookInfo文档id一个的话，那么就比较二者
                if(ObjectUtils.equals(t.getId(),elasticES.getId())){
                    if(!ObjectUtils.equals(book,elasticES)){
                        log.info("update "+t.getId()+" "+Thread.currentThread().getStackTrace()[1].getLineNumber());
                        bookElasticMapper.updateBook(idStr,book);
                    }else log.info("not need to update "+t.getId()+" "+Thread.currentThread().getStackTrace()[1].getLineNumber());
                    atomicInteger.incrementAndGet();
                    return;
                }
                // 如果bookElastic当前指向的文档id大于bookInfo那么就说明bookElastic该文档是多余的，那么就循环遍历，直到最终或者文档id
                if(ObjectUtils.compare(t.getId(),elasticES.getId())>0){
                    while(ObjectUtils.compare(t.getId(),elasticES.getId())>0){
                        //delete elastic search job 之后做
                        log.info("delete "+elasticES.getId()+" "+Thread.currentThread().getStackTrace()[1].getLineNumber());
                        bookElasticMapper.deleteBook(elasticES.getId()+"");
                        index = atomicInteger.incrementAndGet();

                        if(index == bookElasticESS.size()) {
                            log.info("insert "+t.getId());
                            bookElasticMapper.updateBook(t.getId()+"",book);
                            return;
                        }
                        elasticES = bookElasticESS.get(index);
                    }
                    if(ObjectUtils.compare(t.getId(),elasticES.getId())<0){
                        log.info("insert "+t.getId()+" "+Thread.currentThread().getStackTrace()[1].getLineNumber());
                        bookElasticMapper.updateBook(idStr,book);
                        return;
                    }
                    if(!ObjectUtils.equals(book,elasticES)){
                        log.info("update "+t.getId()+" "+Thread.currentThread().getStackTrace()[1].getLineNumber());
                        bookElasticMapper.updateBook(idStr,book);
                    }else log.info("not need to update "+t.getId()+" "+Thread.currentThread().getStackTrace()[1].getLineNumber());
                    atomicInteger.incrementAndGet();
                }
            });
            log.info(LocalDateTime.now()+" finish bookSync");
        } catch (Exception e) {
            log.error("error "+ e.getClass().getSimpleName()+" "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @XxlJob("demoTest")
    @SneakyThrows
    public void demoTest() {
        bookElasticMapper.deleteBook("0");
    }

}
