package com.novel.book.dao;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novel.core.document.BookElasticES;
import com.novel.core.entity.BookInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Component
@Slf4j
public class BookElasticMapper {
    @Autowired
    @Qualifier("getElasticSearchClient")
    ElasticsearchClient elasticsearchClient;

    @Autowired
    private BookInfoMapper bookInfoMapper;

    @SneakyThrows
    public void insertOne(String id, BookElasticES bookElasticES) {
        elasticsearchClient.index(i -> i.index("book")
                                        .document(bookElasticES)
                                        .id(id));
    }

    @SneakyThrows
    public void updateBook(String id, BookElasticES bookElasticES) {
        try {
            elasticsearchClient.index(i -> i.index("book")
                                            .document(bookElasticES)
                                            .id(id));
        } catch (ElasticsearchException e) {
            log.info("error" + e.getClass()
                                .getSimpleName() + "-" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @SneakyThrows
    public void deleteBook(String id){
        DeleteRequest request = DeleteRequest.of(d->d.index("book").id(id));
        DeleteResponse delete = elasticsearchClient.delete(request);
        String jsonValue = delete.result()
                         .jsonValue();
        log.info("result " +jsonValue);
    }

    public List<BookElasticES> searchAllBook() throws IOException {
        SearchRequest searchRequest = SearchRequest.of(s -> s.index("book")
                                                             .query(q -> q.matchAll(m -> m)));
        SearchResponse<BookElasticES> searchPart = elasticsearchClient.search(searchRequest, BookElasticES.class);
        long totalNum = searchPart.hits()
                               .total()
                               .value();
        log.info("total doc size " + totalNum);

        List<BookElasticES> bookElasticES = new ArrayList<>();
        AtomicInteger from = new AtomicInteger(0);
        AtomicInteger size = new AtomicInteger();
        while(totalNum>0){
            size.set((int)(totalNum-100>0?100:totalNum));
            List<BookElasticES> elasticES = getBookElasticES(from, size);

            bookElasticES.addAll(elasticES);
            from.addAndGet(size.get());
            totalNum = totalNum-100>0?totalNum-100:0;
        }
        log.info("after search and merge es list size :"+bookElasticES.size());
        return bookElasticES;
    }

    private List<BookElasticES> getBookElasticES(AtomicInteger from, AtomicInteger size) throws IOException {
        SearchRequest request = SearchRequest.of(s -> s.index("book")
                                                       .query(q -> q.matchAll(m -> m))
                                                       .size(size.get())
                                                       .from(from.get()));
        SearchResponse<BookElasticES> search = elasticsearchClient.search(request, BookElasticES.class);
        List<BookElasticES> elasticES = search.hits()
                                            .hits()
                                            .stream()
                                            .map(t -> t.source())
                                            .collect(Collectors.toList());
        return elasticES;
    }
}
