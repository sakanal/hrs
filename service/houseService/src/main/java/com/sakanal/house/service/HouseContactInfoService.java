package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.house.HouseContactInfoEntity;

import java.util.Map;

/**
 * 房源联系电话表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
public interface HouseContactInfoService extends IService<HouseContactInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

