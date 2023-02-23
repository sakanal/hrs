package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.house.dao.HouseImageDao;
import com.sakanal.service.entity.house.HouseImageEntity;
import com.sakanal.house.service.HouseImageService;


@Service("houseImageService")
public class HouseImageServiceImpl extends ServiceImpl<HouseImageDao, HouseImageEntity> implements HouseImageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseImageEntity> page = this.page(new Query<HouseImageEntity>().getPage(params), new QueryWrapper<HouseImageEntity>());

        return new PageUtils(page);
    }

}
