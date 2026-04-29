package com.ehxy.tsg.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Announcement {
    private Long id;
    private String title;
    private String content;
    private Long publisherId;
    private LocalDateTime publishDate;
    private Integer status;
    
    private Admin publisher;
}
