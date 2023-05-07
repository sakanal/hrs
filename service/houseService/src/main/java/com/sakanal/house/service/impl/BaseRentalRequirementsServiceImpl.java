package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.service.entity.house.BaseRentContentEntity;
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
import org.springframework.util.StringUtils;


@Service("baseRentalRequirementsService")
public class BaseRentalRequirementsServiceImpl extends ServiceImpl<BaseRentalRequirementsDao, BaseRentalRequirementsEntity> implements BaseRentalRequirementsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String state = (String) params.get("state");
        LambdaQueryWrapper<BaseRentalRequirementsEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(state)){
            lambdaQueryWrapper.eq(BaseRentalRequirementsEntity::getShowState,state);
        }

        IPage<BaseRentalRequirementsEntity> page = this.page(new Query<BaseRentalRequirementsEntity>().getPage(params), lambdaQueryWrapper);

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value = {"rentalRequirements"},key = "#root.methodName")
    public List<RentalRequirementsVO> getAll() {
        return this.list().stream().map(RentalRequirementsVO::new).collect(Collectors.toList());
    }

}
