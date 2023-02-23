package com.sakanal.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.user.BusinessHousePublishEntity;

import java.util.Map;

/**
 * 商家房源发布表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
public interface BusinessHousePublishService extends IService<BusinessHousePublishEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

