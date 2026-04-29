package com.ehxy.tsg.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Reader {
    private Long id;
    private String readerNo;
    private String name;
    private String password;
    private String gender;
    private String phone;
    private String email;
    private String department;
    private Integer maxBorrowCount;
    private Integer status;
    private LocalDateTime createTime;
}
