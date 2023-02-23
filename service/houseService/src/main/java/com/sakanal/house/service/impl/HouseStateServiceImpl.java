package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.house.dao.HouseStateDao;
import com.sakanal.service.entity.house.HouseStateEntity;
import com.sakanal.house.service.HouseStateService;


@Service("houseStateService")
public class HouseStateServiceImpl extends ServiceImpl<HouseStateDao, HouseStateEntity> implements HouseStateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseStateEntity> page = this.page(new Query<HouseStateEntity>().getPage(params), new QueryWrapper<HouseStateEntity>());

        return new PageUtils(page);
    }

}
