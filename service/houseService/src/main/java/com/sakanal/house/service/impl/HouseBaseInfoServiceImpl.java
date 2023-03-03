package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.base.constant.AreaLevelConstant;
import com.sakanal.base.constant.CityLevelConstant;
import com.sakanal.house.service.HouseAreaService;
import com.sakanal.house.service.HouseCityService;
import com.sakanal.service.entity.house.HouseAreaEntity;
import com.sakanal.service.entity.house.HouseCityEntity;
import com.sakanal.service.vo.PublishBaseInfoVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.house.dao.HouseBaseInfoDao;
import com.sakanal.service.entity.house.HouseBaseInfoEntity;
import com.sakanal.house.service.HouseBaseInfoService;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;


@Service("houseBaseInfoService")
public class HouseBaseInfoServiceImpl extends ServiceImpl<HouseBaseInfoDao, HouseBaseInfoEntity> implements HouseBaseInfoService {
    @Resource
    private HouseCityService houseCityService;
    @Resource
    private HouseAreaService houseAreaService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseBaseInfoEntity> page = this.page(new Query<HouseBaseInfoEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value = {"HouseBaseInfo"},key = "#root.methodName")
    public PublishBaseInfoVO getPublishBaseInfo(Long cityId) {
        // 根据 市级城市id 获取 省级城市数据
        List<HouseCityEntity> cityList = houseCityService.list(new LambdaQueryWrapper<HouseCityEntity>().eq(HouseCityEntity::getSuperiorId, cityId).eq(HouseCityEntity::getLevel, CityLevelConstant.THIRD));
        // 获取交通路以及小区数据
        List<Long> cityIds = cityList.stream().map(HouseCityEntity::getId).collect(Collectors.toList());
        List<HouseAreaEntity> roadAndAreaList = houseAreaService.list(new LambdaQueryWrapper<HouseAreaEntity>().in(HouseAreaEntity::getCityId, cityIds));
        // 城市相关的交通路
        List<HouseAreaEntity> roadList = roadAndAreaList.stream().filter(houseAreaEntity -> Objects.equals(houseAreaEntity.getLevel(), AreaLevelConstant.FIRST)).collect(Collectors.toList());
        // 城市相关的小区
        List<HouseAreaEntity> areaList = roadAndAreaList.stream().filter(houseAreaEntity -> Objects.equals(houseAreaEntity.getLevel(), AreaLevelConstant.SECOND)).collect(Collectors.toList());
        // 组装数据
        return new PublishBaseInfoVO(cityList,roadList,areaList);
    }

}
