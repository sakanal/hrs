package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.house.entity.HouseCityEntity;

import java.util.List;
import java.util.Map;

/**
 * 房源所在城市表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
public interface HouseCityService extends IService<HouseCityEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<HouseCityEntity> childrenList();

    /**
     * 根据id获取向上有关的父城市id
     */
    List<Long> getRelatedSuperiorIdsById(Long cityId);

    /**
     * 根据id获取其下有关的所有子城市id
     */
    List<Long> getRelatedCityIdsById(Long cityId);
}

