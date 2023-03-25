package com.sakanal.user.listener;

import com.rabbitmq.client.Channel;
import com.sakanal.base.utils.R;
import com.sakanal.service.properties.MyCommonRabbitMQProperties;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.RedisUtils;
import com.sakanal.user.feign.OSSFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Service
@RabbitListener(queues = MyCommonRabbitMQProperties.IMG_OSS_DELAY_QUEUE)
public class imgUselessListener {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private OSSFeignClient ossFeignClient;
    @Resource
    private MyCommonRedisProperties redisProperties;
    @RabbitHandler
    public void listener(String uuid, Channel channel, Message message) throws IOException {
        log.info("获取到过期消息-->{}",uuid);
        String fileUrl = (String) redisUtils.hashGet(redisProperties.getAvatarTempPrefix(), uuid);
        if (StringUtils.hasText(fileUrl)){
            HashMap<String, String> params = new HashMap<>();
            params.put("fileUrl",fileUrl);
            R r = ossFeignClient.remove(params);
            Integer code = (Integer) r.get("code");
            if (code==0){
                redisUtils.hashDel(redisProperties.getAvatarTempPrefix(),uuid);
                log.info("删除成功");
            }else {
                log.error("删除失败");
            }
        }
//        try {
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        } catch (Exception e) {
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
//        }

    }
}
