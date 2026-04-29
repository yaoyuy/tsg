package com.ehxy.tsg.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Book {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publishDate;
    private BigDecimal price;
    private Long categoryId;
    private Integer totalCount;
    private Integer availableCount;
    private String location;
    private String description;
    private String keywords;
    private LocalDateTime createTime;
    private Integer borrowCount;
    
    private BookCategory category;
    private BookDetail detail;
}
