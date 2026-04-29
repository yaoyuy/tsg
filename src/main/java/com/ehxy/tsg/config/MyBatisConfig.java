package com.ehxy.tsg.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ehxy.tsg.mapper")
public class MyBatisConfig {
}
