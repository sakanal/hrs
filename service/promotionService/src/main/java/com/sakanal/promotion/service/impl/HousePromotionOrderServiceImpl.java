package com.sakanal.promotion.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.sakanal.service.vo.PromotionOrderVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


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
        LambdaQueryWrapper<HousePromotionOrderEntity> queryWrapper = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasText(key)){
            queryWrapper.like(HousePromotionOrderEntity::getId,key);
        }
        String userId = (String) params.get("userId");
        if (StringUtils.hasText(userId)){
            queryWrapper.eq(HousePromotionOrderEntity::getUserId,userId);
        }
        queryWrapper.orderByDesc(HousePromotionOrderEntity::getCreatedTime);
        IPage<HousePromotionOrderEntity> page = this.page(new Query<HousePromotionOrderEntity>().getPage(params),queryWrapper);

        List<HousePromotionOrderEntity> records = page.getRecords();
        // 收集推广套餐id（去重）
        Set<Long> setPromotionId = records.stream().map(HousePromotionOrderEntity::getPromotionId).collect(Collectors.toSet());
        // 搜集推广套餐数据<id，name>
        Map<Long, String> mapPromotion = promotionFunctionService.listByIds(setPromotionId).stream().collect(Collectors.toMap(HousePromotionFunctionEntity::getId, HousePromotionFunctionEntity::getName));
        // 收集房源基本信息id
        List<Long> listBaseInfoId = records.stream().map(HousePromotionOrderEntity::getBaseInfoId).collect(Collectors.toList());
        // 搜集房源数据<id,title>
        Map<Long, String> mapHouse = houseFeignClient.getHouseTitleByIds(listBaseInfoId).getData("data", new TypeReference<Map<Long, String>>() {
        });
        List<PromotionOrderVO> resultData = records.stream().map(housePromotionOrderEntity -> {
            PromotionOrderVO promotionOrderVO = new PromotionOrderVO(housePromotionOrderEntity);
            // 设置推广套餐名
            promotionOrderVO.setPromotionName(mapPromotion.get(housePromotionOrderEntity.getPromotionId()));
            // 设置房源标题
            promotionOrderVO.setBaseInfoTitle(mapHouse.get(housePromotionOrderEntity.getBaseInfoId()));
            return promotionOrderVO;
        }).collect(Collectors.toList());
        Page<PromotionOrderVO> promotionOrderVOPage = new Page<>(page.getCurrent(),page.getSize(),page.getTotal());
        promotionOrderVOPage.setRecords(resultData);
        return new PageUtils(promotionOrderVOPage);
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
