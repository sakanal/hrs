package com.sakanal.promotion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.dto.PayAsyncDTO;
import com.sakanal.service.dto.PayDTO;
import com.sakanal.service.dto.PromotionOrderDTO;
import com.sakanal.service.entity.promotion.HousePromotionOrderEntity;

import java.util.Map;

/**
 * 推广活动订单表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-04-16 10:41:39
 */
public interface HousePromotionOrderService extends IService<HousePromotionOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PayDTO createOrder(PromotionOrderDTO promotionOrderDTO);

    boolean handlePayResult(PayAsyncDTO payAsyncDTO);
}

