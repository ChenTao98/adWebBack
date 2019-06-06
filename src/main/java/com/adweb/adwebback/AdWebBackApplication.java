package com.adweb.adwebback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.adweb.adwebback.dao")
public class AdWebBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdWebBackApplication.class, args);
    }

}
