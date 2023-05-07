package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.service.entity.house.BaseRentContentEntity;
import com.sakanal.service.vo.HighlightVO;
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

import com.sakanal.house.dao.BaseHighlightDao;
import com.sakanal.service.entity.house.BaseHighlightEntity;
import com.sakanal.house.service.BaseHighlightService;
import org.springframework.util.StringUtils;


@Service("baseHighlightService")
public class BaseHighlightServiceImpl extends ServiceImpl<BaseHighlightDao, BaseHighlightEntity> implements BaseHighlightService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String state = (String) params.get("state");
        LambdaQueryWrapper<BaseHighlightEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(state)){
            lambdaQueryWrapper.eq(BaseHighlightEntity::getShowState,state);
        }
        IPage<BaseHighlightEntity> page = this.page(new Query<BaseHighlightEntity>().getPage(params), lambdaQueryWrapper);

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value = {"highLight"},key = "#root.methodName")
    public List<HighlightVO> getAll() {
        return this.list().stream().map(HighlightVO::new).collect(Collectors.toList());
    }

}
