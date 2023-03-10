package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.base.constant.AreaLevelConstant;
import com.sakanal.base.constant.CityLevelConstant;
import com.sakanal.base.constant.PublishStateConstant;
import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.exception.MyException;
import com.sakanal.house.service.*;
import com.sakanal.service.dto.*;
import com.sakanal.service.entity.house.*;
import com.sakanal.service.vo.CityWithAreaVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.house.dao.HouseBaseInfoDao;

import javax.annotation.Resource;

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
    @Cacheable(value = {"HouseBaseInfo"},key = "#root.methodName")
    public CityWithAreaVO getPublishBaseInfo(Long cityId) {
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
    public boolean submitPublish(PublishInfoDTO publishInfoDTO) {
        // 存储房源基础信息数据
        CompletableFuture<Long> houseIdCompletableFuture = CompletableFuture.supplyAsync(() -> {
            PublishInfoDTO.BaseInfoDTO baseInfo = publishInfoDTO.getBaseInfo();
            HouseBaseInfoEntity houseBaseInfoEntity = new HouseBaseInfoEntity();
            BeanUtils.copyProperties(baseInfo, houseBaseInfoEntity);
            houseBaseInfoEntity.setCategoryId(0L);
            if (this.save(houseBaseInfoEntity)) {
                return houseBaseInfoEntity.getId();
            } else {
                throw new MyException(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode());
            }
        }, executor);
        houseIdCompletableFuture.thenApplyAsync((houseBaseInfoId) -> {
            // 存储 发布状态 数据
            CompletableFuture.runAsync(()->{
                HouseStateEntity houseStateEntity = new HouseStateEntity();
                houseStateEntity.setBaseInfoId(houseBaseInfoId);
                houseStateEntity.setPublisherId(publishInfoDTO.getPublishId());
                houseStateEntity.setHousePublishState(PublishStateConstant.DEFAULT_STATE);
                if (!stateService.save(houseStateEntity)) {
                    throw new MyException(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode());
                }
            },executor);
            // 存储 租金 信息数据
            CompletableFuture<Void> rentInfoCompletableFuture = CompletableFuture.runAsync(() -> {
                PublishInfoDTO.RentInfoDTO rentInfo = publishInfoDTO.getRentInfo();
                HouseRentInfoEntity houseRentInfoEntity = new HouseRentInfoEntity();
                BeanUtils.copyProperties(rentInfo, houseRentInfoEntity);
                houseRentInfoEntity.setBaseInfoId(houseBaseInfoId);
                houseRentInfoEntity.setRentContentIds(StringUtils.join(rentInfo.getRentContentIds(), "/"));
                if (!rentInfoService.save(houseRentInfoEntity)) {
                    throw new MyException(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode());
                }
            }, executor);
            // 存储 详细 信息数据
            CompletableFuture<Void> detailedInfoCompletableFuture = CompletableFuture.runAsync(() -> {
                PublishInfoDTO.DetailedInfoDTO detailedInfo = publishInfoDTO.getDetailedInfo();
                HouseDetailedInfoEntity houseDetailedInfoEntity = new HouseDetailedInfoEntity();
                BeanUtils.copyProperties(detailedInfo,houseDetailedInfoEntity);
                houseDetailedInfoEntity.setBaseInfoId(houseBaseInfoId);
                houseDetailedInfoEntity.setBaseFacilitiesIds(StringUtils.join(detailedInfo.getBaseFacilitiesIds(), "/"));
                houseDetailedInfoEntity.setBaseHighlightIds(StringUtils.join(detailedInfo.getBaseHighlightIds(),"/"));
                houseDetailedInfoEntity.setBaseRentalRequirementIds(StringUtils.join(detailedInfo.getBaseRentalRequirementIds(),"/"));
                if (!detailedInfoService.save(houseDetailedInfoEntity)){
                    throw new MyException(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode());
                }
            }, executor);
            // 存储 图片 信息数据
            CompletableFuture<Void> imageInfoListCompletableFuture = CompletableFuture.runAsync(() -> {
                List<PublishInfoDTO.ImageInfoDTO> imageInfoList = publishInfoDTO.getImageInfoList();
                ArrayList<HouseImageEntity> houseImageEntityList = new ArrayList<>();
                imageInfoList.forEach(imageInfoDTO -> {
                    HouseImageEntity houseImageEntity = new HouseImageEntity();
                    BeanUtils.copyProperties(imageInfoDTO,houseImageEntity);
                    houseImageEntity.setBaseInfoId(houseBaseInfoId);
                    houseImageEntityList.add(houseImageEntity);
                });
                if (!imageService.saveBatch(houseImageEntityList)) {
                    throw new MyException(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode());
                }
                }, executor);
            // 存储 联系人基础 信息数据
            CompletableFuture<Void> contactInfoCompletableFuture = CompletableFuture.runAsync(() -> {
                PublishInfoDTO.ContactInfoDTO contactInfo = publishInfoDTO.getContactInfo();
                HouseContactInfoEntity houseContactInfoEntity = new HouseContactInfoEntity();
                BeanUtils.copyProperties(contactInfo,houseContactInfoEntity);
                houseContactInfoEntity.setBaseInfoId(houseBaseInfoId);
                if (!contactInfoService.save(houseContactInfoEntity)) {
                    throw new MyException(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode());
                }
                }, executor);

            CompletableFuture<Void> allOf = CompletableFuture.allOf(rentInfoCompletableFuture, detailedInfoCompletableFuture, imageInfoListCompletableFuture, contactInfoCompletableFuture);
            allOf.exceptionally((error)->{
                System.out.println("allOf");
                System.out.println(error.getMessage());
                return null;
            });
            allOf.join();
            return true;
        }, executor);
        houseIdCompletableFuture.exceptionally((error)->{
            System.out.println("baseInfoId");
            System.out.println(error.getMessage());
            return null;
        });

        try {
            return houseIdCompletableFuture.get() != null;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

}
