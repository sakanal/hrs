package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.house.service.*;
import com.sakanal.service.entity.house.HouseBaseInfoEntity;
import com.sakanal.service.vo.PublishBaseInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.house.dao.HouseStateDao;
import com.sakanal.service.entity.house.HouseStateEntity;

import javax.annotation.Resource;


@Service("houseStateService")
public class HouseStateServiceImpl extends ServiceImpl<HouseStateDao, HouseStateEntity> implements HouseStateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseStateEntity> page = this.page(new Query<HouseStateEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

}
