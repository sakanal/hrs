package com.sakanal.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.entity.house.BaseRentalRequirementsEntity;
import com.sakanal.service.vo.RentalRequirementsVO;

import java.util.List;
import java.util.Map;

/**
 * 房屋出租要求表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
public interface BaseRentalRequirementsService extends IService<BaseRentalRequirementsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<RentalRequirementsVO> getAll();
}

