package com.sakanal.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.user.dao.BusinessHousePublishDao;
import com.sakanal.service.entity.user.BusinessHousePublishEntity;
import com.sakanal.user.service.BusinessHousePublishService;


@Service("businessHousePublishService")
public class BusinessHousePublishServiceImpl extends ServiceImpl<BusinessHousePublishDao, BusinessHousePublishEntity> implements BusinessHousePublishService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusinessHousePublishEntity> page = this.page(new Query<BusinessHousePublishEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

}
