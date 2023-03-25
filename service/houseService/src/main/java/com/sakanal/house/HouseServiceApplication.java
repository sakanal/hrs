package com.sakanal.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@ComponentScan(basePackages = {"com.sakanal.base","com.sakanal.service","com.sakanal.house"})
@SpringBootApplication
@EnableScheduling
public class HouseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseServiceApplication.class, args);
    }

}
