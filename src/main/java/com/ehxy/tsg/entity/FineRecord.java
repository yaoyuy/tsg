package com.ehxy.tsg.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FineRecord {
    private Long id;
    private Long readerId;
    private Long borrowId;
    private BigDecimal amount;
    private Integer isPaid;
    private LocalDateTime createTime;
    private Reader reader;
    private BorrowRecord borrowRecord;
}
