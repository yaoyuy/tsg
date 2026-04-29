package com.ehxy.tsg.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Message {
    private Long id;
    private Long readerId;
    private String title;
    private String content;
    private Integer isRead;
    private LocalDateTime createTime;
    private Reader reader;
}
