package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.house.HousePromotionEntity;

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
    /**
     * 从redis中获取对应的浏览人数
     * 如果redis中的数据过期需要从数据库中获取新数据
     */
    Integer getVisitorNumber(Long baseInfoId);

    /**
     * 更新redis中的浏览人数-->（+1）
     */
    boolean incrVisitorNumber(Long baseInfoId);
}

