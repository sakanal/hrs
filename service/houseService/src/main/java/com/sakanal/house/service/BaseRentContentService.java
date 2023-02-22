package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.house.entity.BaseRentContentEntity;

import java.util.Map;

/**
 * 房屋租金内容表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
public interface BaseRentContentService extends IService<BaseRentContentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

