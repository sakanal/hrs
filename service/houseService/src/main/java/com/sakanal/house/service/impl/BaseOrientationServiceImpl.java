package com.sakanal.house.service.impl;

import com.sakanal.service.vo.OrientationVO;
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

import com.sakanal.house.dao.BaseOrientationDao;
import com.sakanal.service.entity.house.BaseOrientationEntity;
import com.sakanal.house.service.BaseOrientationService;


@Service("baseOrientationService")
public class BaseOrientationServiceImpl extends ServiceImpl<BaseOrientationDao, BaseOrientationEntity> implements BaseOrientationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseOrientationEntity> page = this.page(new Query<BaseOrientationEntity>().getPage(params), new QueryWrapper<BaseOrientationEntity>());

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value = {"orientation"},key = "#root.methodName")
    public List<OrientationVO> getAll() {
        return this.list().stream().map(OrientationVO::new).collect(Collectors.toList());
    }

}
