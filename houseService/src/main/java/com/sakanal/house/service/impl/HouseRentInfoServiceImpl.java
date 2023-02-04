package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.Query;

import com.sakanal.house.dao.HouseRentInfoDao;
import com.sakanal.house.entity.HouseRentInfoEntity;
import com.sakanal.house.service.HouseRentInfoService;


@Service("houseRentInfoService")
public class HouseRentInfoServiceImpl extends ServiceImpl<HouseRentInfoDao, HouseRentInfoEntity> implements HouseRentInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseRentInfoEntity> page = this.page(new Query<HouseRentInfoEntity>().getPage(params), new QueryWrapper<HouseRentInfoEntity>());

        return new PageUtils(page);
    }

}
