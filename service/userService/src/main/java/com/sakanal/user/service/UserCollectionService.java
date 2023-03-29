package com.sakanal.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.user.UserCollectionEntity;

import java.util.Map;

/**
 * 用户收藏表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
public interface UserCollectionService extends IService<UserCollectionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils getMyCollection(String token, Integer current);

    boolean isMyCollection(String token, Long houseBaseId);

    boolean addCollection(String token, Long houseBaseId);

    boolean removeCollection(String token, Long houseBaseId);
}

