package com.ehxy.tsg.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Reservation {
    private Long id;
    private Long readerId;
    private Long bookId;
    private LocalDateTime reserveDate;
    private LocalDateTime expireDate;
    private Integer status;
    private LocalDateTime createTime;
    
    private Reader reader;
    private Book book;
}
