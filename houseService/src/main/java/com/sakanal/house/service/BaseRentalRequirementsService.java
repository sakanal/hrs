package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.house.entity.BaseRentalRequirementsEntity;

import java.util.Map;

/**
 * 房屋出租要求表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
public interface BaseRentalRequirementsService extends IService<BaseRentalRequirementsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

