package com.sakanal.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.user.UserBrowseEntity;

import java.util.Map;

/**
 * 用户浏览表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
public interface UserBrowseService extends IService<UserBrowseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void browse(String token, Long houseBaseId);

    PageUtils getMyBrowse(String token,Integer current);
}

