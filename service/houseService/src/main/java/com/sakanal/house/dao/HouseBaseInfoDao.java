package com.sakanal.house.dao;

import com.sakanal.service.entity.house.HouseBaseInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakanal.service.vo.PublishBaseInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 房屋基础信息表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
@Mapper
public interface HouseBaseInfoDao extends BaseMapper<HouseBaseInfoEntity> {

    List<PublishBaseInfoVO> getPublishInfoList(@Param("publishId") Long publishId,
                                               @Param("state") Integer state,
                                               @Param("offset") int offset,
                                               @Param("limit") int limit);
}
