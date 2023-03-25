package com.sakanal.thirdparty;

import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        RedissonAutoConfiguration.class, RedisAutoConfiguration.class,
        RabbitAutoConfiguration.class
})
@ComponentScan(basePackages = {"com.sakanal.base", "com.sakanal.thirdparty"})
public class ThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThirdPartyApplication.class, args);
    }

}
