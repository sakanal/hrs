package com.sakanal.promotion.service.impl;

import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sakanal.promotion.dao.HousePromotionOrderDao;
import com.sakanal.service.entity.promotion.HousePromotionOrderEntity;
import com.sakanal.promotion.service.HousePromotionOrderService;


@Service("housePromotionOrderService")
public class HousePromotionOrderServiceImpl extends ServiceImpl<HousePromotionOrderDao, HousePromotionOrderEntity> implements HousePromotionOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HousePromotionOrderEntity> page = this.page(new Query<HousePromotionOrderEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

}
