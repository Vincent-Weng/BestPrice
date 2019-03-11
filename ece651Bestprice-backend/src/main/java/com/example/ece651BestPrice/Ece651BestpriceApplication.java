package com.example.ece651BestPrice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.example.ece651BestPrice.mapper")
public class Ece651BestpriceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ece651BestpriceApplication.class, args);
    }

}

