package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.Query;

import com.sakanal.house.dao.HouseContactInfoDao;
import com.sakanal.house.entity.HouseContactInfoEntity;
import com.sakanal.house.service.HouseContactInfoService;


@Service("houseContactInfoService")
public class HouseContactInfoServiceImpl extends ServiceImpl<HouseContactInfoDao, HouseContactInfoEntity> implements HouseContactInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseContactInfoEntity> page = this.page(new Query<HouseContactInfoEntity>().getPage(params), new QueryWrapper<HouseContactInfoEntity>());

        return new PageUtils(page);
    }

}
