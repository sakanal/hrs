package com.sakanal.user.dao;

import com.sakanal.user.entity.UserCollectionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户收藏表
 * 
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
@Mapper
public interface UserCollectionDao extends BaseMapper<UserCollectionEntity> {
	
}
