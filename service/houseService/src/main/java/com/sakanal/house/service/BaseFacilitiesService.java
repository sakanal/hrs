package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.house.BaseFacilitiesEntity;
import com.sakanal.service.vo.FacilitiesVO;

import java.util.List;
import java.util.Map;

/**
 * 房屋设施表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
public interface BaseFacilitiesService extends IService<BaseFacilitiesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<FacilitiesVO> getAll();
}

