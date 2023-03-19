package com.sakanal.house.dao;

import com.sakanal.service.dto.PublishInfoListDTO;
import com.sakanal.service.vo.PublishBaseInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HouseDao {

    List<PublishBaseInfoVO> getPublishInfoList(PublishInfoListDTO publishInfoListDTO);

    long countPublishList(PublishInfoListDTO publishInfoListDTO);
}
