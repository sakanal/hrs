package com.sakanal.house.service;

import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.dto.PublishInfoDTO;
import com.sakanal.service.dto.PublishInfoListDTO;
import com.sakanal.service.vo.PublishInfoVO;

public interface HouseService {
    boolean submitPublish(PublishInfoDTO publishInfoDTO);

    boolean updatePublish(PublishInfoDTO publishInfoDTO);

    PageUtils getPublishInfoList(PublishInfoListDTO publishInfoListDTO);

    PublishInfoVO getUpdatePublishInfo(Long houseBaseInfoId);

    PublishInfoVO getPublishInfo(Long houseBaseInfoId);

    boolean changePublishState(Long houseBaseInfoId, Integer state);
}
