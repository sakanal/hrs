package com.sakanal.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.user.entity.UserBaseInfoEntity;

import java.util.Map;

/**
 * 用户基础数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
public interface UserBaseInfoService extends IService<UserBaseInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

