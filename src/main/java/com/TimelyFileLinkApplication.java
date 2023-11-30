package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author 灵感大王
 * @Date 2023/11/26
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.util","com.controller","com.config","com.service","com.mapper","com.strategy","com.util"})
@MapperScan(value = "com.mapper")
public class TimelyFileLinkApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(TimelyFileLinkApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TimelyFileLinkApplication.class);
    }
}
