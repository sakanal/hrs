package com.sakanal.promotion.service.impl;

import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sakanal.promotion.dao.HousePromotionFunctionDao;
import com.sakanal.service.entity.promotion.HousePromotionFunctionEntity;
import com.sakanal.promotion.service.HousePromotionFunctionService;


@Service("housePromotionFunctionService")
public class HousePromotionFunctionServiceImpl extends ServiceImpl<HousePromotionFunctionDao, HousePromotionFunctionEntity> implements HousePromotionFunctionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HousePromotionFunctionEntity> page = this.page(new Query<HousePromotionFunctionEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public boolean changeState(Long promotionId, Integer showState) {
        HousePromotionFunctionEntity housePromotionFunctionEntity = new HousePromotionFunctionEntity();
        housePromotionFunctionEntity.setId(promotionId);
        housePromotionFunctionEntity.setShowState(showState);
        return this.updateById(housePromotionFunctionEntity);
    }

}
