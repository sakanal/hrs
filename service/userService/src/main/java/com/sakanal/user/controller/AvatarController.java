package com.sakanal.user.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.sakanal.base.utils.R;
import com.sakanal.service.entity.user.UserBaseInfoEntity;
import com.sakanal.service.properties.MyCommonRabbitMQProperties;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.user.service.UserBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/avatar")
public class AvatarController {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private MyCommonRedisProperties redisProperties;
    @Resource
    private UserBaseInfoService userBaseInfoService;

    @RequestMapping("/temp")
    public R temp(@RequestBody Map<String,Object> params){
        String fileUrl = (String) params.get("fileUrl");
        BoundHashOperations<String, String, Object> avatarOperations = redisTemplate.boundHashOps(redisProperties.getAvatarTempPrefix());
        String uuid = IdUtil.getSnowflakeNextIdStr();
        avatarOperations.put(uuid,fileUrl);
        rabbitTemplate.convertAndSend(MyCommonRabbitMQProperties.IMG_OSS_RELEASE_EXCHANGE,
                MyCommonRabbitMQProperties.IMG_OSS_RELEASE_ROUTING_KEY,
                uuid);
        return R.ok().put("data",uuid);
    }
    @RequestMapping("/save")
    public R save(@RequestBody Map<String,Object> params){
        if (userBaseInfoService.updateAvatar(params)){
            return R.ok();
        }else {
            return R.error();
        }
    }

}
