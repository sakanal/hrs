package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.dto.PublishInfoDTO;
import com.sakanal.service.entity.house.HouseBaseInfoEntity;
import com.sakanal.service.vo.PublishBaseInfoVO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 房屋基础信息表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
public interface HouseBaseInfoService extends IService<HouseBaseInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PublishBaseInfoVO getPublishBaseInfo(Long cityId);

    boolean submitPublish(PublishInfoDTO publishInfoDTO);
}

