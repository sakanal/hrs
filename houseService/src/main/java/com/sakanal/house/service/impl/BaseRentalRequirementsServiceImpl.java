package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.Query;

import com.sakanal.house.dao.BaseRentalRequirementsDao;
import com.sakanal.house.entity.BaseRentalRequirementsEntity;
import com.sakanal.house.service.BaseRentalRequirementsService;


@Service("baseRentalRequirementsService")
public class BaseRentalRequirementsServiceImpl extends ServiceImpl<BaseRentalRequirementsDao, BaseRentalRequirementsEntity> implements BaseRentalRequirementsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseRentalRequirementsEntity> page = this.page(new Query<BaseRentalRequirementsEntity>().getPage(params), new QueryWrapper<BaseRentalRequirementsEntity>());

        return new PageUtils(page);
    }

}
