package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.constant.ShowStatusConstant;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import com.sakanal.house.dao.HouseAreaDao;
import com.sakanal.house.entity.HouseAreaEntity;
import com.sakanal.house.service.HouseAreaService;
import com.sakanal.house.service.HouseCityService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.sakanal.base.constant.ShowStatusConstant.HIDE_STATUS;
import static com.sakanal.base.constant.ShowStatusConstant.SHOW_STATUS;


@Service("houseAreaService")
public class HouseAreaServiceImpl extends ServiceImpl<HouseAreaDao, HouseAreaEntity> implements HouseAreaService {
    @Resource
    private HouseCityService houseCityService;

    @Override
    @Cacheable(value = {"houseArea"},key = "#root.methodName+'::'+#params['limit']+'+'+#params['cityId']+'+'+#params['key']")
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        LambdaQueryWrapper<HouseAreaEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(key)){
            // 存在搜索字段，进行搜索；包括交通路和住宅小区
            lambdaQueryWrapper.like(HouseAreaEntity::getName,key)
                    .orderByAsc(HouseAreaEntity::getLevel);
        }else {
            // 不存在搜索字段，只搜索交通路
            lambdaQueryWrapper.eq(HouseAreaEntity::getSuperiorId,0);
        }
        String cityId = (String) params.get("cityId");
        if (StringUtils.hasText(cityId)){
            List<Long> cityIds = houseCityService.getRelatedCityIdsById(Long.valueOf(cityId));
            lambdaQueryWrapper.in(HouseAreaEntity::getCityId,cityIds);
        }

        IPage<HouseAreaEntity> page = new Query<HouseAreaEntity>().getPage(params);
        // 分页获取结果数据(road-area)
        page = this.page(page, lambdaQueryWrapper);
        List<HouseAreaEntity> houseAreaEntityList = page.getRecords();

        // 获取到的道路数据
        List<HouseAreaEntity> roadList = houseAreaEntityList.stream().filter(houseAreaEntity -> houseAreaEntity.getSuperiorId() == 0).collect(Collectors.toList());
        // 获取到的住宅区数据
        List<HouseAreaEntity> areaList = houseAreaEntityList.stream().filter(houseAreaEntity -> houseAreaEntity.getSuperiorId() != 0).collect(Collectors.toList());

        // TODO 会有bug 最终出现的数据可能会小于pageSize,但是并没有到最后一页
        List<Long> roadIds = roadList.stream().map(HouseAreaEntity::getId).collect(Collectors.toList());
        if (roadIds.size()>0){
            // 存在道路数据
            // 原始道路数据下的所有住宅数据
            List<HouseAreaEntity> needAreaList = this.list(new LambdaQueryWrapper<HouseAreaEntity>().in(HouseAreaEntity::getSuperiorId,roadIds));
            if (areaList.size()>0){
                // 条件搜索 搜索到了住宅区数据
                // 去除重复的住宅区数据
                areaList.removeAll(needAreaList);
                if (areaList.size()>0){
                    //如果原始住宅区数据去除包含在通过原始道路数据获取到的住宅区数据后，还有其他道路的独立数据
                    Set<Long> roadIdSet = areaList.stream().map(HouseAreaEntity::getSuperiorId).collect(Collectors.toSet());
                    // 获取最终的道路数据
                    roadList.addAll(this.listByIds(roadIdSet));
                }
                // 获取最终的住宅区数据
                areaList.addAll(needAreaList);
            }else {
                // 非条件搜索或没有搜索到住宅区数据
                areaList.addAll(needAreaList);
            }
        }else {
            // 不存在道路数据
            Set<Long> roadIdSet = areaList.stream().map(HouseAreaEntity::getSuperiorId).collect(Collectors.toSet());
            roadList.addAll(this.listByIds(roadIdSet));
        }
        // 组装数据
        houseAreaEntityList = fromResultList(roadList,areaList);
        page.setRecords(houseAreaEntityList);
        return new PageUtils(page);
    }
    @Override
    @Cacheable(value = {"houseArea"},key = "#root.methodName+'::'+#root.args")
    public List<HouseAreaEntity> childrenList(Long cityId) {
        LambdaQueryWrapper<HouseAreaEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (cityId!=null && cityId!=0){
            lambdaQueryWrapper.eq(HouseAreaEntity::getCityId,cityId);
        }
        List<HouseAreaEntity> houseAreaEntityList = this.list(lambdaQueryWrapper);
        List<HouseAreaEntity> houseOneList = houseAreaEntityList.stream().filter(houseAreaEntity -> houseAreaEntity.getSuperiorId() == 0).collect(Collectors.toList());
        //组装
        return fromResultList(houseOneList,houseAreaEntityList);
    }
    private List<HouseAreaEntity> fromResultList(List<HouseAreaEntity> houseOneList, List<HouseAreaEntity> root){
        return houseOneList.stream().peek(houseAreaEntity -> {
            List<HouseAreaEntity> childrenList = getGroupArea(houseAreaEntity, root);
            if (childrenList!=null && childrenList.size()>0){
                for (HouseAreaEntity cityEntity : childrenList) {
                    getGroupArea(cityEntity,root);
                }
            }
        }).collect(Collectors.toList());
    }
    private List<HouseAreaEntity> getGroupArea(HouseAreaEntity houseAreaEntity,List<HouseAreaEntity> houseAreaEntityList){
        Long oneId = houseAreaEntity.getId();
        List<HouseAreaEntity> childrenList = houseAreaEntityList.stream().filter(houseArea -> Objects.equals(houseArea.getSuperiorId(), oneId)).collect(Collectors.toList());
        if (childrenList.size()>0){
            houseAreaEntity.setHasChildren(true);
            houseAreaEntity.setChildrenList(childrenList);
            houseAreaEntity.setChildren(childrenList);
            return childrenList;
        }else {
            houseAreaEntity.setHasChildren(false);
            return null;
        }
    }

    @Override
    @CacheEvict(value = {"houseArea"},allEntries = true)
    public boolean updateShowStateById(HouseAreaEntity houseAreaEntity) {
        if (houseAreaEntity.getSuperiorId() == 0){
            //修改的是交通路
            if (Objects.equals(houseAreaEntity.getShowState(), HIDE_STATUS)){
                //从显示变成隐藏，其下的住宅区也变成隐藏
                List<HouseAreaEntity> houseAreaList = this.list(new LambdaQueryWrapper<HouseAreaEntity>().eq(HouseAreaEntity::getSuperiorId, houseAreaEntity.getId()));
                List<HouseAreaEntity> collect = houseAreaList.stream().map(houseArea -> {
                    HouseAreaEntity areaEntity = new HouseAreaEntity();
                    areaEntity.setId(houseArea.getId());
                    areaEntity.setShowState(HIDE_STATUS);
                    return areaEntity;
                }).collect(Collectors.toList());
                collect.add(houseAreaEntity);
                return this.updateBatchById(collect);
            }else {
                //从隐藏变成显示，不会影响其下的住宅区
                return this.updateById(houseAreaEntity);
            }
        }else {
            //修改的是住宅区
            if (Objects.equals(houseAreaEntity.getShowState(),HIDE_STATUS)){
                // 从显示变隐藏，住宅区变为隐藏不会影响所在街道
                return this.updateById(houseAreaEntity);
            }else {
                // 从隐藏变显示，如果街道是隐藏状态，需要一起变为显示
                HouseAreaEntity road = this.getOne(new LambdaQueryWrapper<HouseAreaEntity>().eq(HouseAreaEntity::getId, houseAreaEntity.getSuperiorId()));
                road.setShowState(SHOW_STATUS);
                List<HouseAreaEntity> areaEntityList = new ArrayList<>();
                areaEntityList.add(houseAreaEntity);
                areaEntityList.add(road);
                return this.updateBatchById(areaEntityList);
            }
        }
    }


}
