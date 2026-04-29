package com.ehxy.tsg.entity;

import lombok.Data;

@Data
public class BookTag {
    private Long id;
    private Long bookId;
    private Long tagId;
    
    private Book book;
    private Tag tag;
}
