package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.house.BaseOrientationEntity;
import com.sakanal.service.vo.OrientationVO;

import java.util.List;
import java.util.Map;

/**
 * 房屋朝向表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
public interface BaseOrientationService extends IService<BaseOrientationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<OrientationVO> getAll();
}

