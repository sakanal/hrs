package com.sakanal.house.dao;

import com.sakanal.service.entity.house.HouseRentInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 房源租金信息表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
@Mapper
public interface HouseRentInfoDao extends BaseMapper<HouseRentInfoEntity> {

}
