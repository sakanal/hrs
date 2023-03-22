package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.house.entity.HousePromotionEntity;

import java.util.Map;

/**
 * 房源推广数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-03-22 16:19:43
 */
public interface HousePromotionService extends IService<HousePromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

