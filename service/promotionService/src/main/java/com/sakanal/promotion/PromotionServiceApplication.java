package com.sakanal.promotion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@ComponentScan(basePackages = {"com.sakanal.base","com.sakanal.service","com.sakanal.promotion"})
@SpringBootApplication
@EnableScheduling
public class PromotionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PromotionServiceApplication.class,args);
    }

}
