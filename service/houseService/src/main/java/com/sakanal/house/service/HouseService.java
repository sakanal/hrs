package com.sakanal.house.service;

import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.dto.PublishInfoDTO;
import com.sakanal.service.vo.PublishInfoVO;

public interface HouseService {
    boolean submitPublish(PublishInfoDTO publishInfoDTO);

    boolean updatePublish(PublishInfoDTO publishInfoDTO);

    PageUtils getPublishInfoList(Long publishId, Integer state, Integer current);

    PublishInfoVO getPublishInfo(String houseBaseInfoId);

}
