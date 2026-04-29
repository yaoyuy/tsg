package com.ehxy.tsg.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Review {
    private Long id;
    private Long readerId;
    private Long bookId;
    private Integer rating;
    private String content;
    private LocalDateTime createTime;
    private Integer status;
    private Reader reader;
    private Book book;
}
