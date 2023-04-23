package com.sakanal.house.dao;

import com.sakanal.service.entity.house.HousePromotionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 房源推广数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-03-22 16:19:43
 */
@Mapper
public interface HousePromotionDao extends BaseMapper<HousePromotionEntity> {

    boolean addPromotionNumber(@Param("baseInfoId") Long baseInfoId,@Param("promotionNumber") Long promotionNumber);

    void reducePromotionNumber(@Param("baseInfoIdList") List<Long> baseInfoIdList);
}
