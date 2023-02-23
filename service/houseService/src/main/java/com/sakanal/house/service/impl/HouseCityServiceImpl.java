package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.house.dao.HouseCityDao;
import com.sakanal.service.entity.house.HouseCityEntity;
import com.sakanal.house.service.HouseCityService;

import javax.annotation.Resource;


@Service("houseCityService")
public class HouseCityServiceImpl extends ServiceImpl<HouseCityDao, HouseCityEntity> implements HouseCityService {
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseCityEntity> page = this.page(new Query<HouseCityEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value = {"houseCity"},key = "#root.methodName")
    public List<HouseCityEntity> childrenList(){
        //数据库中的所有未删除的数据
        List<HouseCityEntity> houseCityList = this.list();
        //获取:省/自治区/直辖市
        List<HouseCityEntity> houseOneList = houseCityList.stream().filter(houseCityEntity -> houseCityEntity.getSuperiorId() == 0).collect(Collectors.toList());
        //组装
        return houseOneList.stream().peek(houseCityEntity -> {
            //组装 省/自治区/直辖市->市
            List<HouseCityEntity> childrenList = getGroupCity(houseCityEntity, houseCityList);
            if (childrenList!=null && childrenList.size()>0){
                //组装 市->区
                for (HouseCityEntity cityEntity : childrenList) {
                    getGroupCity(cityEntity,houseCityList);
                }
            }
        }).collect(Collectors.toList());
    }
    private List<HouseCityEntity> getGroupCity(HouseCityEntity houseCityEntity,List<HouseCityEntity> houseCityList){
        Long oneId = houseCityEntity.getId();
        List<HouseCityEntity> childrenList = houseCityList.stream().filter(houseCity -> Objects.equals(houseCity.getSuperiorId(), oneId)).collect(Collectors.toList());
        if (childrenList.size()>0){
            houseCityEntity.setHasChildren(true);
            houseCityEntity.setChildrenList(childrenList);
            return childrenList;
        }else {
            houseCityEntity.setHasChildren(false);
            return null;
        }
    }


    @Override
    public List<Long> getRelatedSuperiorIdsById(Long cityId) {
        HouseCityEntity houseCityEntity = baseMapper.selectById(cityId);
        return getSuperiorIds(houseCityEntity);
    }
    private List<Long> getSuperiorIds(HouseCityEntity houseCityEntity){
        if (houseCityEntity.getSuperiorId() == 0){
            return new ArrayList<>();
        }
        HouseCityEntity superiorHouseCityEntity = baseMapper.selectById(houseCityEntity.getSuperiorId());
        List<Long> longList = getSuperiorIds(superiorHouseCityEntity);
        longList.add(superiorHouseCityEntity.getId());
        return longList;

    }

    @Override
    public List<Long> getRelatedCityIdsById(Long cityId) {
        HouseCityEntity houseCityEntity = this.getById(cityId);
        return getCityIds(houseCityEntity);
    }
    private List<Long> getCityIds(HouseCityEntity houseCityEntity){
        if (houseCityEntity == null){
            return new ArrayList<>();
        }
        List<HouseCityEntity> houseCityEntityList = baseMapper.selectList(new LambdaQueryWrapper<HouseCityEntity>().eq(HouseCityEntity::getSuperiorId, houseCityEntity.getId()));
        List<Long> allCityIds = new ArrayList<>();
        allCityIds.add(houseCityEntity.getId());
        if (houseCityEntityList!=null && houseCityEntityList.size()>0){
            houseCityEntityList.forEach(houseCity -> {
                List<Long> cityIds = getCityIds(houseCity);
                allCityIds.addAll(cityIds);
            });
        }
        return allCityIds;
    }




}
