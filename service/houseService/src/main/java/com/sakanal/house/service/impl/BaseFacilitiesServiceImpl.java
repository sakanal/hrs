package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.base.constant.ShowStateConstant;
import com.sakanal.service.entity.house.BaseHighlightEntity;
import com.sakanal.service.entity.house.BaseRentContentEntity;
import com.sakanal.service.vo.FacilitiesVO;
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

import com.sakanal.house.dao.BaseFacilitiesDao;
import com.sakanal.service.entity.house.BaseFacilitiesEntity;
import com.sakanal.house.service.BaseFacilitiesService;
import org.springframework.util.StringUtils;


@Service("baseFacilitiesService")
public class BaseFacilitiesServiceImpl extends ServiceImpl<BaseFacilitiesDao, BaseFacilitiesEntity> implements BaseFacilitiesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String state = (String) params.get("state");
        LambdaQueryWrapper<BaseFacilitiesEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(state)){
            lambdaQueryWrapper.eq(BaseFacilitiesEntity::getShowState,state);
        }
        IPage<BaseFacilitiesEntity> page = this.page(new Query<BaseFacilitiesEntity>().getPage(params), lambdaQueryWrapper);

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value = {"facilities"},key = "#root.methodName")
    public List<FacilitiesVO> getAll() {
        return this.list(new LambdaQueryWrapper<BaseFacilitiesEntity>().eq(BaseFacilitiesEntity::getShowState, ShowStateConstant.SHOW_STATUS))
                .stream().map(FacilitiesVO::new).collect(Collectors.toList());
    }

}
