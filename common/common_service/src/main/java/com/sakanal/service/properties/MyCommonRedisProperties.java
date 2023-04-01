package com.sakanal.service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("common.redis")
public class MyCommonRedisProperties {
    private Long expireTime = (long) 60 * 60 * 24 * 7;
    private Long visitorExpireTime = (long) 60 * 60;
    private Long codeExpireTime = (long) 60 * 5;
    private String userInfoPrefix = "userInfo::";
    private String visitorPrefix = "visitor::";
    private String avatarTempPrefix = "avatar::temp";
    private String codePrefix = "code::";
}
