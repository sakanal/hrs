package com.sakanal.promotion.listener;

import com.rabbitmq.client.Channel;
import com.sakanal.base.constant.OrderStateConstant;
import com.sakanal.promotion.service.HousePromotionOrderService;
import com.sakanal.service.entity.promotion.HousePromotionOrderEntity;
import com.sakanal.service.properties.MyCommonRabbitMQProperties;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Service
@RabbitListener(queues = MyCommonRabbitMQProperties.ORDER_DELAY_QUEUE)
public class orderUselessListener {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private HousePromotionOrderService promotionOrderService;
    @Resource
    private MyCommonRedisProperties redisProperties;

    @RabbitHandler
    public void listener(Long orderId, Channel channel, Message message) {
        log.info("获取到过期消息（订单超时未支付）-->{}", orderId);
        if (redisUtils.hasKey(redisProperties.getOrderPrefix()+orderId)) {
            // 30分钟内未支付订单
            HousePromotionOrderEntity housePromotionOrderEntity = new HousePromotionOrderEntity();
            housePromotionOrderEntity.setId(orderId);
            housePromotionOrderEntity.setState(OrderStateConstant.FAIL);
            if (promotionOrderService.updateById(housePromotionOrderEntity)) {
                log.info("订单取消成功");
                redisUtils.del(String.valueOf(orderId));
            }
        }
//        try {
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        } catch (Exception e) {
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
//        }

    }
}
