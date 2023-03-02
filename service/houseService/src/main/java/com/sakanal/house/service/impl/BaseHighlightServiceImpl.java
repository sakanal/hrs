package com.sakanal.house.service.impl;

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


@Service("baseHighlightService")
public class BaseHighlightServiceImpl extends ServiceImpl<BaseHighlightDao, BaseHighlightEntity> implements BaseHighlightService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseHighlightEntity> page = this.page(new Query<BaseHighlightEntity>().getPage(params), new QueryWrapper<BaseHighlightEntity>());

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value = {"Highlight"},key = "#root.methodName")
    public List<HighlightVO> getAll() {
        return this.list().stream().map(HighlightVO::new).collect(Collectors.toList());
    }

}
