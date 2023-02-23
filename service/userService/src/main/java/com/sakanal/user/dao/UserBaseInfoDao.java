package com.sakanal.user.dao;

import com.sakanal.service.entity.user.UserBaseInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户基础数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
@Mapper
public interface UserBaseInfoDao extends BaseMapper<UserBaseInfoEntity> {

}
