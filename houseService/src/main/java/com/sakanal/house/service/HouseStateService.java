package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.house.entity.HouseStateEntity;

import java.util.Map;

/**
 * 房源状态表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:46
 */
public interface HouseStateService extends IService<HouseStateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

