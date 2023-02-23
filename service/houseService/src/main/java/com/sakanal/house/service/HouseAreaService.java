package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.house.HouseAreaEntity;

import java.util.List;
import java.util.Map;

/**
 * 房屋区域表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
public interface HouseAreaService extends IService<HouseAreaEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<HouseAreaEntity> childrenList(Long cityId);

    boolean updateShowStateById(HouseAreaEntity houseAreaEntity);
}

