package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.house.dao.HouseBaseInfoDao;
import com.sakanal.house.entity.HouseBaseInfoEntity;
import com.sakanal.house.service.HouseBaseInfoService;


@Service("houseBaseInfoService")
public class HouseBaseInfoServiceImpl extends ServiceImpl<HouseBaseInfoDao, HouseBaseInfoEntity> implements HouseBaseInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseBaseInfoEntity> page = this.page(new Query<HouseBaseInfoEntity>().getPage(params), new QueryWrapper<HouseBaseInfoEntity>());

        return new PageUtils(page);
    }

}
