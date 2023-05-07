package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.base.constant.ShowStateConstant;
import com.sakanal.service.vo.RentContentVO;
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

import com.sakanal.house.dao.BaseRentContentDao;
import com.sakanal.service.entity.house.BaseRentContentEntity;
import com.sakanal.house.service.BaseRentContentService;
import org.springframework.util.StringUtils;


@Service("baseRentContentService")
public class BaseRentContentServiceImpl extends ServiceImpl<BaseRentContentDao, BaseRentContentEntity> implements BaseRentContentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String state = (String) params.get("state");
        LambdaQueryWrapper<BaseRentContentEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(state)){
            lambdaQueryWrapper.eq(BaseRentContentEntity::getShowState,state);
        }
        IPage<BaseRentContentEntity> page = this.page(new Query<BaseRentContentEntity>().getPage(params),lambdaQueryWrapper);

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value = {"rentContent"},key = "#root.methodName")
    public List<RentContentVO> getAll() {
        return this.list(new LambdaQueryWrapper<BaseRentContentEntity>().eq(BaseRentContentEntity::getShowState, ShowStateConstant.SHOW_STATUS))
                .stream().map(RentContentVO::new).collect(Collectors.toList());
    }

}
