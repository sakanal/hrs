package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.house.HouseDetailedInfoEntity;

import java.util.Map;

/**
 * 房源详细介绍表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-05 12:48:19
 */
public interface HouseDetailedInfoService extends IService<HouseDetailedInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

