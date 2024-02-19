package com.novel.book.config;

import com.novel.book.dao.BookInfoMapper;
import com.novel.core.entity.BookInfo;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
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

    private final BookInfoMapper bookInfoMapper;


    public void transferData() {

        List<BookInfo> allBook = bookInfoMapper.getAll();
        allBook.stream()
               .forEach(t -> {
                   BookElastic bookElastic = new BookElastic();
                   BeanUtils.copyProperties(t, bookElastic);
                   LocalDateTime localDateTime = t.getLastChapterUpdateTime();
                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'");
                   String formattedDateTime = localDateTime.format(formatter);
                   bookElastic.setLastChapterUpdateTime(formattedDateTime);
                   try {
                       IndexResponse response = elasticsearchClient.index(i -> i.index("book")
                                                                                .document(bookElastic)
                                                                                .id(t.getId() + ""));
                       log.info("sss");
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               });
    }

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
