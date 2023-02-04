package com.sakanal.house.dao;

import com.sakanal.house.entity.HouseStateEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 房源状态表
 * 
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:46
 */
@Mapper
public interface HouseStateDao extends BaseMapper<HouseStateEntity> {
	
}
