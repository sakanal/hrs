package com.sakanal.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.sakanal.common","com.sakanal.service","com.sakanal.house"})
@SpringBootApplication
public class HouseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseServiceApplication.class, args);
    }

}
