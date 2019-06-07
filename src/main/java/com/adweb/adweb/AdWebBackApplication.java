package com.adweb.adweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.adweb.adweb.dao")
public class AdWebBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdWebBackApplication.class, args);
    }

}
