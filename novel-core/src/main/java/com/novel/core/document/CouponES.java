package com.novel.core.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class CouponES implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @JsonFormat( shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat( shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    private Integer limitNums;
    private Restrict restrict;
    @Data
    public static class Restrict implements Serializable{
        private static final long serialVersionUID = 1L;

        private Double discount;
        private Double maxValue;
        private Double minCost;
        private Short type;
    }
}
