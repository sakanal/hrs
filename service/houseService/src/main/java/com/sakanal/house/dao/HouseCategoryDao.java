package com.sakanal.house.dao;

import com.sakanal.house.entity.HouseCategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 房屋类别表
 * 
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
@Mapper
public interface HouseCategoryDao extends BaseMapper<HouseCategoryEntity> {
	
}
