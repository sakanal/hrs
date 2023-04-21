package com.sakanal.promotion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import com.sakanal.promotion.dao.HousePromotionOrderDao;
import com.sakanal.promotion.service.HousePromotionFunctionService;
import com.sakanal.promotion.service.HousePromotionOrderService;
import com.sakanal.service.dto.PromotionOrderDTO;
import com.sakanal.service.entity.promotion.HousePromotionFunctionEntity;
import com.sakanal.service.entity.promotion.HousePromotionOrderEntity;
import com.sakanal.service.properties.MyCommonRabbitMQProperties;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.RedisUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;


@Service("housePromotionOrderService")
public class HousePromotionOrderServiceImpl extends ServiceImpl<HousePromotionOrderDao, HousePromotionOrderEntity> implements HousePromotionOrderService {
    @Resource
    private MyCommonRedisProperties redisProperties;
    @Resource
    private HousePromotionFunctionService promotionFunctionService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HousePromotionOrderEntity> page = this.page(new Query<HousePromotionOrderEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public boolean createOrder(PromotionOrderDTO promotionOrderDTO) {
        HousePromotionFunctionEntity promotionFunction = promotionFunctionService.getById(promotionOrderDTO.getPromotionId());
        if (promotionFunction != null) {
            promotionOrderDTO.setTotalMoney(promotionFunction.getPrice());
            HousePromotionOrderEntity housePromotionOrderEntity = new HousePromotionOrderEntity(promotionOrderDTO);
            boolean save = this.save(housePromotionOrderEntity);
            if (save) {
                // 延时订单设置
                Long orderId = housePromotionOrderEntity.getId();
                rabbitTemplate.convertAndSend(MyCommonRabbitMQProperties.IMG_OSS_RELEASE_EXCHANGE,
                        MyCommonRabbitMQProperties.ORDER_RELEASE_ROUTING_KEY,
                        orderId);
                redisUtils.stringSet(redisProperties.getOrderPrefix()+orderId, new Date());
                return true;
            }
        }
        return false;
    }

}
