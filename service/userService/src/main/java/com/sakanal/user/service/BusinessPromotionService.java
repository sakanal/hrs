package com.sakanal.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.user.entity.BusinessPromotionEntity;

import java.util.Map;

/**
 * 商家推广数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
public interface BusinessPromotionService extends IService<BusinessPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

