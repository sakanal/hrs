package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.house.HouseRentInfoEntity;

import java.util.Map;

/**
 * 房源租金信息表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
public interface HouseRentInfoService extends IService<HouseRentInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

