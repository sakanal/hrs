package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.Query;

import com.sakanal.house.dao.BaseFacilitiesDao;
import com.sakanal.house.entity.BaseFacilitiesEntity;
import com.sakanal.house.service.BaseFacilitiesService;


@Service("baseFacilitiesService")
public class BaseFacilitiesServiceImpl extends ServiceImpl<BaseFacilitiesDao, BaseFacilitiesEntity> implements BaseFacilitiesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseFacilitiesEntity> page = this.page(new Query<BaseFacilitiesEntity>().getPage(params), new QueryWrapper<BaseFacilitiesEntity>());

        return new PageUtils(page);
    }

}
