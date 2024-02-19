package com.novel.pay.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.novel.core.entity.BookInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MySQLToElasticsearchImporter {
    @Autowired
    @Qualifier("getElasticSearchClient")
    ElasticsearchClient elasticsearchClient;


    @Data
    @EqualsAndHashCode
    class BookElastic implements Serializable {

        private final Long serialVersionUID = 1L;


        private Integer workDirection;


        private Long categoryId;


        private String categoryName;


        private String picUrl;


        private String bookName;

        private Long authorId;


        private String authorName;


        private String bookDesc;

        private Integer score;


        private Integer bookStatus;


        private Long visitCount;


        private Integer wordCount;


        private Integer commentCount;


        private Long lastChapterId;


        private String lastChapterName;


        private String lastChapterUpdateTime;


        private Integer isVip;


        private LocalDateTime createTime;

        private LocalDateTime updateTime;
    }

}
