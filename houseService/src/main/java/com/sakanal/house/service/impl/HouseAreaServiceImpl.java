package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.Query;

import com.sakanal.house.dao.HouseAreaDao;
import com.sakanal.house.entity.HouseAreaEntity;
import com.sakanal.house.service.HouseAreaService;


@Service("houseAreaService")
public class HouseAreaServiceImpl extends ServiceImpl<HouseAreaDao, HouseAreaEntity> implements HouseAreaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseAreaEntity> page = this.page(new Query<HouseAreaEntity>().getPage(params), new QueryWrapper<HouseAreaEntity>());

        return new PageUtils(page);
    }

}
