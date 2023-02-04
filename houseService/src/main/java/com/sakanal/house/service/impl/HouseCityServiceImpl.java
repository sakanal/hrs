package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.Query;

import com.sakanal.house.dao.HouseCityDao;
import com.sakanal.house.entity.HouseCityEntity;
import com.sakanal.house.service.HouseCityService;


@Service("houseCityService")
public class HouseCityServiceImpl extends ServiceImpl<HouseCityDao, HouseCityEntity> implements HouseCityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseCityEntity> page = this.page(new Query<HouseCityEntity>().getPage(params), new QueryWrapper<HouseCityEntity>());

        return new PageUtils(page);
    }

}
