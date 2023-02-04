package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.Query;

import com.sakanal.house.dao.HouseDetailedInfoDao;
import com.sakanal.house.entity.HouseDetailedInfoEntity;
import com.sakanal.house.service.HouseDetailedInfoService;


@Service("houseDetailedInfoService")
public class HouseDetailedInfoServiceImpl extends ServiceImpl<HouseDetailedInfoDao, HouseDetailedInfoEntity> implements HouseDetailedInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseDetailedInfoEntity> page = this.page(new Query<HouseDetailedInfoEntity>().getPage(params), new QueryWrapper<HouseDetailedInfoEntity>());

        return new PageUtils(page);
    }

}
