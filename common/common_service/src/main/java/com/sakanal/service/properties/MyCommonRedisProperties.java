package com.sakanal.service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("common.redis")
public class MyCommonRedisProperties {
    private Long expireTime = 1000L * 60 * 60 * 24 * 7;
    private Long visitorExpireTime = 1000L * 60 * 60;
    private String userInfoPrefix = "userInfo::";
    private String visitorPrefix = "visitor::";
    private String avatarTempPrefix = "avatar::temp";
}
