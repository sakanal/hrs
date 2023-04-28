package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.constant.AreaLevelConstant;
import com.sakanal.base.constant.CityLevelConstant;
import com.sakanal.base.constant.PageConstant;
import com.sakanal.base.constant.PublishStateConstant;
import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.exception.MyException;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import com.sakanal.house.dao.HouseBaseInfoDao;
import com.sakanal.house.service.*;
import com.sakanal.service.dto.PublishInfoDTO;
import com.sakanal.service.entity.house.*;
import com.sakanal.service.vo.CityWithAreaVO;
import com.sakanal.service.vo.PublishBaseInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
@Service("houseBaseInfoService")
public class HouseBaseInfoServiceImpl extends ServiceImpl<HouseBaseInfoDao, HouseBaseInfoEntity> implements HouseBaseInfoService {
    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private HouseRentInfoService rentInfoService;
    @Resource
    private HouseDetailedInfoService detailedInfoService;
    @Resource
    private HouseImageService imageService;
    @Resource
    private HouseContactInfoService contactInfoService;
    @Resource
    private HouseStateService stateService;
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
    @Cacheable(value = {"houseBaseInfo"},key = "#root.methodName")
    public CityWithAreaVO getCityWithAreaInfo(Long cityId) {
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
        return new CityWithAreaVO(cityList,roadList,areaList);
    }

    @Override
    public List<PublishBaseInfoVO> getPublishInfoList(Long publishId, Integer state, int offset, Integer limit) {
        return baseMapper.getPublishInfoList(publishId,state,offset,limit);
    }


}
