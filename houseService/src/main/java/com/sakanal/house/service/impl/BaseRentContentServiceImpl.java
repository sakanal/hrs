package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.Query;

import com.sakanal.house.dao.BaseRentContentDao;
import com.sakanal.house.entity.BaseRentContentEntity;
import com.sakanal.house.service.BaseRentContentService;


@Service("baseRentContentService")
public class BaseRentContentServiceImpl extends ServiceImpl<BaseRentContentDao, BaseRentContentEntity> implements BaseRentContentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseRentContentEntity> page = this.page(new Query<BaseRentContentEntity>().getPage(params), new QueryWrapper<BaseRentContentEntity>());

        return new PageUtils(page);
    }

}
