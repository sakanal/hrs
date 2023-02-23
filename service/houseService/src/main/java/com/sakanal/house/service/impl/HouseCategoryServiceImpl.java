package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.house.dao.HouseCategoryDao;
import com.sakanal.service.entity.house.HouseCategoryEntity;
import com.sakanal.house.service.HouseCategoryService;


@Service("houseCategoryService")
public class HouseCategoryServiceImpl extends ServiceImpl<HouseCategoryDao, HouseCategoryEntity> implements HouseCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseCategoryEntity> page = this.page(new Query<HouseCategoryEntity>().getPage(params), new QueryWrapper<HouseCategoryEntity>());

        return new PageUtils(page);
    }

}
