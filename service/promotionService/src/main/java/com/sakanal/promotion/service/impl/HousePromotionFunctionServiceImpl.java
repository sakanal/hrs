package com.sakanal.promotion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import org.springframework.util.StringUtils;


@Service("housePromotionFunctionService")
public class HousePromotionFunctionServiceImpl extends ServiceImpl<HousePromotionFunctionDao, HousePromotionFunctionEntity> implements HousePromotionFunctionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<HousePromotionFunctionEntity> queryWrapper = new LambdaQueryWrapper<>();
        String showState = (String)params.get("showState");
        if (StringUtils.hasText(showState)){
            queryWrapper.eq(HousePromotionFunctionEntity::getShowState,Integer.valueOf(showState));
        }
        String key = (String) params.get("key");
        if (StringUtils.hasText(key)){
            queryWrapper.like(HousePromotionFunctionEntity::getName,key);
        }
        IPage<HousePromotionFunctionEntity> page = this.page(new Query<HousePromotionFunctionEntity>().getPage(params),queryWrapper);

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
