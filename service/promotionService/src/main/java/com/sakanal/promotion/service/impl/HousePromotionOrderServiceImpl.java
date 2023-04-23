package com.sakanal.promotion.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.constant.OrderStateConstant;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import com.sakanal.promotion.dao.HousePromotionOrderDao;
import com.sakanal.promotion.feign.HouseFeignClient;
import com.sakanal.promotion.service.HousePromotionFunctionService;
import com.sakanal.promotion.service.HousePromotionOrderService;
import com.sakanal.service.dto.PayAsyncDTO;
import com.sakanal.service.dto.PayDTO;
import com.sakanal.service.dto.PromotionNumberDTO;
import com.sakanal.service.dto.PromotionOrderDTO;
import com.sakanal.service.entity.promotion.HousePromotionFunctionEntity;
import com.sakanal.service.entity.promotion.HousePromotionOrderEntity;
import com.sakanal.service.properties.MyCommonRabbitMQProperties;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.RedisUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Resource
    private HouseFeignClient houseFeignClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HousePromotionOrderEntity> page = this.page(new Query<HousePromotionOrderEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public PayDTO createOrder(PromotionOrderDTO promotionOrderDTO) {
        HousePromotionFunctionEntity promotionFunction = promotionFunctionService.getById(promotionOrderDTO.getPromotionId());
        promotionOrderDTO.setTotalMoney(promotionFunction.getPrice());
        if (promotionOrderDTO.getBaseInfoId()!=null){
            // 只给该房源推广
            return createOrder(promotionOrderDTO,promotionFunction);
        }else {
            // 给该用户的所有房源进行推广
            Long count = houseFeignClient.countPublish(promotionOrderDTO.getUserId()).getData("count", new TypeReference<Long>() {
            });
            promotionOrderDTO.setBaseInfoId(-count);
            return createOrder(promotionOrderDTO,promotionFunction);
        }
    }
    private PayDTO createOrder(PromotionOrderDTO promotionOrderDTO,HousePromotionFunctionEntity promotionFunction){
        if (promotionFunction != null) {
            HousePromotionOrderEntity housePromotionOrderEntity = new HousePromotionOrderEntity(promotionOrderDTO);
            boolean save = this.save(housePromotionOrderEntity);
            if (save) {
                // 延时订单设置
                Long orderId = housePromotionOrderEntity.getId();
                rabbitTemplate.convertAndSend(MyCommonRabbitMQProperties.IMG_OSS_RELEASE_EXCHANGE,
                        MyCommonRabbitMQProperties.ORDER_RELEASE_ROUTING_KEY,
                        orderId);
                redisUtils.stringSet(redisProperties.getOrderPrefix()+orderId, new Date());
                String subject = promotionFunctionService.getById(promotionOrderDTO.getPromotionId()).getName();
                BigDecimal totalMoney = promotionOrderDTO.getTotalMoney();
                return new PayDTO(orderId,subject,totalMoney);
            }
        }
        return null;
    }

    @Override
    public boolean handlePayResult(PayAsyncDTO payAsyncDTO) {
        String tradeStatus = payAsyncDTO.getTrade_status();
        if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
            //支付成功状态
            long orderId = Long.parseLong(payAsyncDTO.getOut_trade_no());//获取订单号
            redisUtils.del(redisProperties.getOrderPrefix()+orderId);


            HousePromotionOrderEntity housePromotionOrderEntity = new HousePromotionOrderEntity();
            housePromotionOrderEntity.setId(orderId);
            housePromotionOrderEntity.setShowState(OrderStateConstant.SUCCESS);
            if (this.updateById(housePromotionOrderEntity)){
                // 获取订单信息
                HousePromotionOrderEntity promotionOrder = this.getById(orderId);
                Long number = promotionFunctionService.getById(promotionOrder.getPromotionId()).getNumber();
                PromotionNumberDTO promotionNumberDTO = new PromotionNumberDTO(promotionOrder);
                promotionNumberDTO.setPromotionNumber(number*promotionOrder.getNumber());
                // 添加推广数据
                Integer code = houseFeignClient.addPromotion(promotionNumberDTO).getData("code", new TypeReference<Integer>() {
                });
                return code==0;
            }
        }
        return false;
    }

}
