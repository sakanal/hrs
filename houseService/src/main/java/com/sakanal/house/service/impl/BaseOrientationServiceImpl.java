package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.Query;

import com.sakanal.house.dao.BaseOrientationDao;
import com.sakanal.house.entity.BaseOrientationEntity;
import com.sakanal.house.service.BaseOrientationService;


@Service("baseOrientationService")
public class BaseOrientationServiceImpl extends ServiceImpl<BaseOrientationDao, BaseOrientationEntity> implements BaseOrientationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseOrientationEntity> page = this.page(new Query<BaseOrientationEntity>().getPage(params), new QueryWrapper<BaseOrientationEntity>());

        return new PageUtils(page);
    }

}
