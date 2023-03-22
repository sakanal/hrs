package com.sakanal.house.service.impl;

import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sakanal.house.dao.HousePromotionDao;
import com.sakanal.house.entity.HousePromotionEntity;
import com.sakanal.house.service.HousePromotionService;


@Service("housePromotionService")
public class HousePromotionServiceImpl extends ServiceImpl<HousePromotionDao, HousePromotionEntity> implements HousePromotionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HousePromotionEntity> page = this.page(new Query<HousePromotionEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

}
