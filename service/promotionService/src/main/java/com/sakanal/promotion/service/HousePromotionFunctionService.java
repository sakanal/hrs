package com.sakanal.promotion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.promotion.HousePromotionFunctionEntity;

import java.util.Map;

/**
 * 推广活动表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-04-16 10:41:39
 */
public interface HousePromotionFunctionService extends IService<HousePromotionFunctionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

