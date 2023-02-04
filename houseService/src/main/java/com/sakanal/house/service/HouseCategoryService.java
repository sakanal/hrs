package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.house.entity.HouseCategoryEntity;

import java.util.Map;

/**
 * 房屋类别表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
public interface HouseCategoryService extends IService<HouseCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

