package com.sakanal.house.dao;

import com.sakanal.service.dto.PublishInfoListDTO;
import com.sakanal.service.dto.RecommendInfoListDTO;
import com.sakanal.service.vo.PublishBaseInfoVO;
import com.sakanal.service.vo.RecommendInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HouseDao {

    List<PublishBaseInfoVO> getPublishInfoList(PublishInfoListDTO publishInfoListDTO);

    long countPublishList(PublishInfoListDTO publishInfoListDTO);

    List<RecommendInfoVO> getRecommendInfoList(RecommendInfoListDTO recommendInfoListDTO);
}
