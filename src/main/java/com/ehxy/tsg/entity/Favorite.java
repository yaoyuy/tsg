package com.ehxy.tsg.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Favorite {
    private Long id;
    private Long readerId;
    private Long bookId;
    private LocalDateTime createTime;
    private Reader reader;
    private Book book;
}
