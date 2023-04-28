package com.sakanal.house.service.impl;

import com.sakanal.service.vo.RentalRequirementsVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.house.dao.BaseRentalRequirementsDao;
import com.sakanal.service.entity.house.BaseRentalRequirementsEntity;
import com.sakanal.house.service.BaseRentalRequirementsService;


@Service("baseRentalRequirementsService")
public class BaseRentalRequirementsServiceImpl extends ServiceImpl<BaseRentalRequirementsDao, BaseRentalRequirementsEntity> implements BaseRentalRequirementsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseRentalRequirementsEntity> page = this.page(new Query<BaseRentalRequirementsEntity>().getPage(params), new QueryWrapper<BaseRentalRequirementsEntity>());

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value = {"rentalRequirements"},key = "#root.methodName")
    public List<RentalRequirementsVO> getAll() {
        return this.list().stream().map(RentalRequirementsVO::new).collect(Collectors.toList());
    }

}
