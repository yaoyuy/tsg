package com.ehxy.tsg.entity;

import lombok.Data;

@Data
public class BookCategory {
    private Long id;
    private String categoryName;
    private Long parentId;
    private Integer sortOrder;
    private String description;
}
