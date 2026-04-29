package com.ehxy.tsg.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BorrowRecord {
    private Long id;
    private Long readerId;
    private Long bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BigDecimal fine;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    
    private Reader reader;
    private Book book;
}
