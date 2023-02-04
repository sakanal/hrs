package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.house.entity.BaseHighlightEntity;

import java.util.Map;

/**
 * 房屋亮点表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
public interface BaseHighlightService extends IService<BaseHighlightEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

