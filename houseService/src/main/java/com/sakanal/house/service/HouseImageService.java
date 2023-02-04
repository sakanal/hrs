package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.house.entity.HouseImageEntity;

import java.util.Map;

/**
 * 房源图片表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
public interface HouseImageService extends IService<HouseImageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

