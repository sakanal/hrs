package com.sakanal.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.Query;

import com.sakanal.user.dao.BusinessPromotionDao;
import com.sakanal.user.entity.BusinessPromotionEntity;
import com.sakanal.user.service.BusinessPromotionService;


@Service("businessPromotionService")
public class BusinessPromotionServiceImpl extends ServiceImpl<BusinessPromotionDao, BusinessPromotionEntity> implements BusinessPromotionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusinessPromotionEntity> page = this.page(new Query<BusinessPromotionEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

}
