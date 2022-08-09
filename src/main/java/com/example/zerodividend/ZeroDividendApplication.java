package com.example.zerodividend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@Slf4j
public class ZeroDividendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeroDividendApplication.class, args);
    }

}
