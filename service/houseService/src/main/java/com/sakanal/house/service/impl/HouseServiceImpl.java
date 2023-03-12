package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.base.constant.PageConstant;
import com.sakanal.base.constant.PublishStateConstant;
import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.exception.MyException;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.house.service.*;
import com.sakanal.service.dto.PublishInfoDTO;
import com.sakanal.service.entity.house.*;
import com.sakanal.service.vo.PublishBaseInfoVO;
import com.sakanal.service.vo.PublishInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HouseServiceImpl implements HouseService {
    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private HouseBaseInfoService baseInfoService;
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
    public boolean submitPublish(PublishInfoDTO publishInfoDTO) {
        // 存储房源基础信息数据
        CompletableFuture<Long> houseIdCompletableFuture = CompletableFuture.supplyAsync(() -> {
            PublishInfoDTO.BaseInfoDTO baseInfo = publishInfoDTO.getBaseInfo();
            HouseBaseInfoEntity houseBaseInfoEntity = new HouseBaseInfoEntity();
            BeanUtils.copyProperties(baseInfo, houseBaseInfoEntity);
            houseBaseInfoEntity.setCategoryId(0L);
            if (baseInfoService.save(houseBaseInfoEntity)) {
                return houseBaseInfoEntity.getId();
            } else {
                throw new MyException(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode());
            }
        }, executor);
        houseIdCompletableFuture.thenApplyAsync((houseBaseInfoId) -> {
            // 存储 发布状态 数据
            CompletableFuture.runAsync(() -> {
                HouseStateEntity houseStateEntity = new HouseStateEntity();
                houseStateEntity.setBaseInfoId(houseBaseInfoId);
                houseStateEntity.setPublisherId(publishInfoDTO.getPublishId());
                houseStateEntity.setHousePublishState(PublishStateConstant.EXAMINE_STATE);
                if (!stateService.save(houseStateEntity)) {
                    throw new MyException(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode());
                }
            }, executor);
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
                // TODO hasElevator和hasParkingPlace 无法保存，全部保存为0
                PublishInfoDTO.DetailedInfoDTO detailedInfo = publishInfoDTO.getDetailedInfo();
                HouseDetailedInfoEntity houseDetailedInfoEntity = new HouseDetailedInfoEntity();
                BeanUtils.copyProperties(detailedInfo, houseDetailedInfoEntity);
                houseDetailedInfoEntity.setBaseInfoId(houseBaseInfoId);
                houseDetailedInfoEntity.setBaseFacilitiesIds(StringUtils.join(detailedInfo.getBaseFacilitiesIds(), "/"));
                houseDetailedInfoEntity.setBaseHighlightIds(StringUtils.join(detailedInfo.getBaseHighlightIds(), "/"));
                houseDetailedInfoEntity.setBaseRentalRequirementIds(StringUtils.join(detailedInfo.getBaseRentalRequirementIds(), "/"));
                if (!detailedInfoService.save(houseDetailedInfoEntity)) {
                    throw new MyException(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode());
                }
            }, executor);
            // 存储 图片 信息数据
            CompletableFuture<Void> imageInfoListCompletableFuture = CompletableFuture.runAsync(() -> {
                List<PublishInfoDTO.ImageInfoDTO> imageInfoList = publishInfoDTO.getImageInfoList();
                ArrayList<HouseImageEntity> houseImageEntityList = new ArrayList<>();
                imageInfoList.forEach(imageInfoDTO -> {
                    HouseImageEntity houseImageEntity = new HouseImageEntity();
                    BeanUtils.copyProperties(imageInfoDTO, houseImageEntity);
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
                BeanUtils.copyProperties(contactInfo, houseContactInfoEntity);
                houseContactInfoEntity.setBaseInfoId(houseBaseInfoId);
                if (!contactInfoService.save(houseContactInfoEntity)) {
                    throw new MyException(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode());
                }
            }, executor);

            CompletableFuture<Void> allOf = CompletableFuture.allOf(rentInfoCompletableFuture, detailedInfoCompletableFuture, imageInfoListCompletableFuture, contactInfoCompletableFuture);
            allOf.exceptionally((error) -> {
                System.out.println("allOf");
                System.out.println(error.getMessage());
                return null;
            });
            allOf.join();
            return true;
        }, executor);
        houseIdCompletableFuture.exceptionally((error) -> {
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

    @Override
    public boolean updatePublish(PublishInfoDTO publishInfoDTO) {
        Long baseInfoId = publishInfoDTO.getPublishId();
        // 更改状态表
        CompletableFuture<Void> stateCompletableFuture = CompletableFuture.runAsync(() -> {
            HouseStateEntity houseStateEntity = new HouseStateEntity();
            houseStateEntity.setHousePublishState(PublishStateConstant.EXAMINE_STATE);
            stateService.update(houseStateEntity, new LambdaQueryWrapper<HouseStateEntity>().eq(HouseStateEntity::getBaseInfoId, baseInfoId));
        }, executor);
        // 更新房源基础信息数据
        CompletableFuture<Void> baseInfoCompletableFuture = stateCompletableFuture.thenRunAsync(() -> {
            PublishInfoDTO.BaseInfoDTO baseInfo = publishInfoDTO.getBaseInfo();
            HouseBaseInfoEntity houseBaseInfoEntity = new HouseBaseInfoEntity();
            BeanUtils.copyProperties(baseInfo, houseBaseInfoEntity);
            houseBaseInfoEntity.setId(baseInfoId);
            houseBaseInfoEntity.setCategoryId(0L);
            houseBaseInfoEntity.setName(baseInfo.getHouseTitle());
            baseInfoService.updateById(houseBaseInfoEntity);
        }, executor);
        // 更新租金信息数据
        CompletableFuture<Void> rentInfoCompletableFuture = stateCompletableFuture.thenRunAsync(() -> {
            PublishInfoDTO.RentInfoDTO rentInfo = publishInfoDTO.getRentInfo();
            HouseRentInfoEntity houseRentInfoEntity = new HouseRentInfoEntity();
            BeanUtils.copyProperties(rentInfo, houseRentInfoEntity);
            houseRentInfoEntity.setBaseInfoId(baseInfoId);
            houseRentInfoEntity.setRentContentIds(StringUtils.join(rentInfo.getRentContentIds(), "/"));
            rentInfoService.update(houseRentInfoEntity, new LambdaQueryWrapper<HouseRentInfoEntity>().eq(HouseRentInfoEntity::getBaseInfoId, baseInfoId));
        }, executor);
        // 更新详细信息数据
        CompletableFuture<Void> detailedInfoCompletableFuture = stateCompletableFuture.thenRunAsync(() -> {
            PublishInfoDTO.DetailedInfoDTO detailedInfo = publishInfoDTO.getDetailedInfo();
            HouseDetailedInfoEntity houseDetailedInfoEntity = new HouseDetailedInfoEntity();
            BeanUtils.copyProperties(detailedInfo, houseDetailedInfoEntity);
            houseDetailedInfoEntity.setBaseInfoId(baseInfoId);
            houseDetailedInfoEntity.setBaseFacilitiesIds(StringUtils.join(detailedInfo.getBaseFacilitiesIds(), "/"));
            houseDetailedInfoEntity.setBaseHighlightIds(StringUtils.join(detailedInfo.getBaseHighlightIds(), "/"));
            houseDetailedInfoEntity.setBaseRentalRequirementIds(StringUtils.join(detailedInfo.getBaseRentalRequirementIds(), "/"));
            detailedInfoService.update(houseDetailedInfoEntity, new LambdaQueryWrapper<HouseDetailedInfoEntity>().eq(HouseDetailedInfoEntity::getBaseInfoId, baseInfoId));
        }, executor);
        // 更新图片信息数据
        CompletableFuture<Void> imageInfoCompletableFuture = stateCompletableFuture.thenRunAsync(() -> {
            ArrayList<HouseImageEntity> houseImageEntityList = new ArrayList<>();
            publishInfoDTO.getImageInfoList().forEach(imageInfoDTO -> {
                HouseImageEntity houseImageEntity = new HouseImageEntity();
                BeanUtils.copyProperties(imageInfoDTO, houseImageEntity);
                houseImageEntity.setBaseInfoId(baseInfoId);
                houseImageEntityList.add(houseImageEntity);
            });
            List<HouseImageEntity> sourceImageList = imageService.list(new LambdaQueryWrapper<HouseImageEntity>().eq(HouseImageEntity::getBaseInfoId, baseInfoId));
            ArrayList<HouseImageEntity> deleteImageList = new ArrayList<>(sourceImageList);
            deleteImageList.removeAll(houseImageEntityList);
            ArrayList<HouseImageEntity> updateImageList = new ArrayList<>(sourceImageList);
            updateImageList.retainAll(houseImageEntityList);
            houseImageEntityList.removeAll(updateImageList);

            imageService.removeBatchByIds(deleteImageList.stream().map(HouseImageEntity::getId).collect(Collectors.toList()));
            imageService.updateBatchById(updateImageList);
            imageService.saveBatch(houseImageEntityList);
        }, executor);
        // 更新联系人信息数据
        CompletableFuture<Void> contactInfoCompletableFuture = stateCompletableFuture.thenRunAsync(() -> {
            PublishInfoDTO.ContactInfoDTO contactInfo = publishInfoDTO.getContactInfo();
            HouseContactInfoEntity houseContactInfoEntity = new HouseContactInfoEntity();
            BeanUtils.copyProperties(contactInfo, houseContactInfoEntity);
            houseContactInfoEntity.setBaseInfoId(baseInfoId);
            contactInfoService.update(houseContactInfoEntity, new LambdaQueryWrapper<HouseContactInfoEntity>().eq(HouseContactInfoEntity::getBaseInfoId, baseInfoId));
        }, executor);

        CompletableFuture.allOf(baseInfoCompletableFuture,rentInfoCompletableFuture,detailedInfoCompletableFuture,imageInfoCompletableFuture,contactInfoCompletableFuture).join();
        return true;
    }

    @Override
    public PageUtils getPublishInfoList(Long publishId, Integer state, Integer current) {
        int offset = (current - 1) * PageConstant.LIMIT;
        LambdaQueryWrapper<HouseStateEntity> totalQueryWrapper = new LambdaQueryWrapper<HouseStateEntity>().eq(HouseStateEntity::getPublisherId, publishId);
        if (state != null) {
            totalQueryWrapper.eq(HouseStateEntity::getHousePublishState, state);
        }
        long totalCount = stateService.count(totalQueryWrapper);
        // 获取数据，当前数据没有房源所在道路名称，只有所在道路id
        List<PublishBaseInfoVO> publishBaseInfoVOList = baseInfoService.getPublishInfoList(publishId, state, offset, PageConstant.LIMIT);
        if (publishBaseInfoVOList != null && publishBaseInfoVOList.size() > 0) {
            // 获取房源所在道路
            Set<Long> roadIds = publishBaseInfoVOList.stream().map(PublishBaseInfoVO::getRoadId).collect(Collectors.toSet());
            if (roadIds.size() > 0) {
                HashMap<Long, String> roadMap = new HashMap<>();
                houseAreaService.listByIds(roadIds).forEach(houseAreaEntity -> roadMap.put(houseAreaEntity.getId(), houseAreaEntity.getName()));
                publishBaseInfoVOList.forEach(publishBaseInfoVO -> publishBaseInfoVO.setRoadName(roadMap.get(publishBaseInfoVO.getRoadId())));
            }
            // 获取房源的所有有关图片
            Set<Long> baseInfoIds = publishBaseInfoVOList.stream().map(PublishBaseInfoVO::getBaseInfoId).collect(Collectors.toSet());
            if (baseInfoIds.size() > 0) {
                List<HouseImageEntity> imageList = imageService.list(new LambdaQueryWrapper<HouseImageEntity>().in(HouseImageEntity::getBaseInfoId, baseInfoIds));
                publishBaseInfoVOList.forEach(publishBaseInfoVO -> {
                    Long baseInfoId = publishBaseInfoVO.getBaseInfoId();
                    List<String> needImageList = imageList.stream().filter(houseImageEntity -> Objects.equals(houseImageEntity.getBaseInfoId(), baseInfoId))
                            .map(HouseImageEntity::getUrl)
                            .collect(Collectors.toList());
                    publishBaseInfoVO.setImageList(needImageList);
                });
            }

            return new PageUtils(publishBaseInfoVOList, Math.toIntExact(totalCount), PageConstant.LIMIT, current);
        }
        return null;
    }

    @Override
    public PublishInfoVO getPublishInfo(String houseBaseInfoId) {
        PublishInfoVO publishInfoVO = new PublishInfoVO();
        publishInfoVO.setHouseBaseInfoId(Long.valueOf(houseBaseInfoId));
        // 获取基本信息
        CompletableFuture<Void> baseInfoCompletableFuture = CompletableFuture.runAsync(() -> {
            HouseBaseInfoEntity baseInfo = baseInfoService.getById(houseBaseInfoId);
            publishInfoVO.setBaseInfo(new PublishInfoVO.BaseInfoVO(baseInfo));
        }, executor).thenRunAsync(() -> {
            List<Long> relatedIds = houseCityService.getRelatedSuperiorIdsById(publishInfoVO.getBaseInfo().getCityId());
            publishInfoVO.setCityWithAreaVO(baseInfoService.getCityWithAreaInfo(relatedIds.get(relatedIds.size() - 1)));
            Long roadId = houseAreaService.getRelatedSuperiorIdById(publishInfoVO.getBaseInfo().getAreaId());
            publishInfoVO.getBaseInfo().setRoadId(roadId);
        }, executor);
        // 获取租金信息
        CompletableFuture<Void> rentInfoCompletableFuture = CompletableFuture.runAsync(() -> {
            HouseRentInfoEntity rentInfo = rentInfoService.getOne(new LambdaQueryWrapper<HouseRentInfoEntity>().eq(HouseRentInfoEntity::getBaseInfoId, houseBaseInfoId));
            publishInfoVO.setRentInfo(new PublishInfoVO.RentInfoVO(rentInfo));
        }, executor);
        // 获取详细信息
        CompletableFuture<Void> detailedInfoCompletableFuture = CompletableFuture.runAsync(() -> {
            HouseDetailedInfoEntity detailedInfo = detailedInfoService.getOne(new LambdaQueryWrapper<HouseDetailedInfoEntity>().eq(HouseDetailedInfoEntity::getBaseInfoId, houseBaseInfoId));
            publishInfoVO.setDetailedInfo(new PublishInfoVO.DetailedInfoVO(detailedInfo));
        }, executor);
        // 获取图片信息
        CompletableFuture<Void> imageListCompletableFuture = CompletableFuture.runAsync(() -> {
            List<HouseImageEntity> imageList = imageService.list(new LambdaQueryWrapper<HouseImageEntity>().eq(HouseImageEntity::getBaseInfoId, houseBaseInfoId));
            List<PublishInfoVO.ImageInfoVO> imageInfoVOList = new ArrayList<>();
            imageList.forEach(imageEntity -> imageInfoVOList.add(new PublishInfoVO.ImageInfoVO(imageEntity)));
            publishInfoVO.setImageInfoList(imageInfoVOList);
        }, executor);
        // 获取联系人信息
        CompletableFuture<Void> contactInfoCompletableFuture = CompletableFuture.runAsync(() -> {
            HouseContactInfoEntity contactInfo = contactInfoService.getOne(new LambdaQueryWrapper<HouseContactInfoEntity>().eq(HouseContactInfoEntity::getBaseInfoId, houseBaseInfoId));
            publishInfoVO.setContactInfo(new PublishInfoVO.ContactInfoVO(contactInfo));
        }, executor);

        CompletableFuture.allOf(baseInfoCompletableFuture, rentInfoCompletableFuture, detailedInfoCompletableFuture, imageListCompletableFuture, contactInfoCompletableFuture).join();
        return publishInfoVO;
    }
}
