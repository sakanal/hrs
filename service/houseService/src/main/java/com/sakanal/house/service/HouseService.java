package com.sakanal.house.service;

import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.dto.PublishInfoDTO;
import com.sakanal.service.dto.PublishInfoListDTO;
import com.sakanal.service.dto.RecommendInfoListDTO;
import com.sakanal.service.vo.PublishInfoVO;
import com.sakanal.service.vo.RecommendInfoVO;

import java.util.List;

public interface HouseService {
    boolean submitPublish(PublishInfoDTO publishInfoDTO);

    boolean updatePublish(PublishInfoDTO publishInfoDTO);

    PageUtils getPublishInfoList(PublishInfoListDTO publishInfoListDTO);

    PublishInfoVO getUpdatePublishInfo(Long houseBaseInfoId);

    PublishInfoVO getPublishInfo(Long houseBaseInfoId);

    boolean changePublishState(Long houseBaseInfoId, Integer state);

    List<RecommendInfoVO> getRecommendList(RecommendInfoListDTO recommendInfoListDTO);

    List<RecommendInfoVO> getRecommendList(Long baseInfoId);
}
