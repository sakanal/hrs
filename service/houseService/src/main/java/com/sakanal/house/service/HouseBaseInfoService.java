package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.dto.PublishInfoDTO;
import com.sakanal.service.entity.house.HouseBaseInfoEntity;
import com.sakanal.service.vo.CityWithAreaVO;
import com.sakanal.service.vo.PublishBaseInfoVO;

import java.util.List;
import java.util.Map;

/**
 * 房屋基础信息表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
public interface HouseBaseInfoService extends IService<HouseBaseInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    CityWithAreaVO getCityWithAreaInfo(Long cityId);

    List<PublishBaseInfoVO> getPublishInfoList(Long publishId, Integer state, int offset, Integer limit);
}

