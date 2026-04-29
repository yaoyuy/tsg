package com.ehxy.tsg.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BookDetail {
    private Long id;
    private Long bookId;
    private Integer pageCount;
    private String language;
    private String edition;
    private String format;
    private BigDecimal weight;
    private String dimensions;
    private String summary;
    
    private Book book;
}
