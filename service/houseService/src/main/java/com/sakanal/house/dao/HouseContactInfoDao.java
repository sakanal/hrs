package com.sakanal.house.dao;

import com.sakanal.service.entity.house.HouseContactInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 房源联系电话表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
@Mapper
public interface HouseContactInfoDao extends BaseMapper<HouseContactInfoEntity> {

    boolean updatePublisherNameByUserId(@Param("userId") Long userId, @Param("nickName") String nickName);

    boolean updatePublisherPhoneByUserId(@Param("userId") long userId, @Param("phone") String phone);
}
