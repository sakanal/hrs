package com.sakanal.house.dao;

import com.sakanal.house.entity.HouseCityEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 房源所在城市表
 * 
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
@Mapper
public interface HouseCityDao extends BaseMapper<HouseCityEntity> {
	
}
