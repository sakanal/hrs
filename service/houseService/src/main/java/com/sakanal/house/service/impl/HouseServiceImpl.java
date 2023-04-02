package com.sakanal.house.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sakanal.base.constant.CityLevelConstant;
import com.sakanal.base.constant.PageConstant;
import com.sakanal.base.constant.PublishStateConstant;
import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.exception.MyException;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import com.sakanal.house.dao.HouseDao;
import com.sakanal.house.feign.UserFeignClient;
import com.sakanal.house.service.*;
import com.sakanal.service.dto.PublishInfoDTO;
import com.sakanal.service.dto.PublishInfoListDTO;
import com.sakanal.service.dto.RecommendInfoListDTO;
import com.sakanal.service.entity.house.*;
import com.sakanal.service.entity.user.UserBaseInfoEntity;
import com.sakanal.service.vo.PublishBaseInfoVO;
import com.sakanal.service.vo.PublishInfoVO;
import com.sakanal.service.vo.RecommendInfoVO;
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
    private HouseDao houseDao;
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
    @Resource
    private BaseRentContentService baseRentContentService;
    @Resource
    private BaseFacilitiesService baseFacilitiesService;
    @Resource
    private BaseHighlightService baseHighlightService;
    @Resource
    private BaseOrientationService baseOrientationService;
    @Resource
    private BaseRentalRequirementsService baseRentalRequirementsService;
    @Resource
    private HousePromotionService promotionService;

    @Resource
    private UserFeignClient userFeignClient;


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

        CompletableFuture.allOf(baseInfoCompletableFuture, rentInfoCompletableFuture, detailedInfoCompletableFuture, imageInfoCompletableFuture, contactInfoCompletableFuture).join();
        return true;
    }

    /**
     * 添加所有图片以及获取道路id
     */
    public void setPublishBaseOtherInfo(List<PublishBaseInfoVO> publishBaseInfoVOList) {
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
        }
    }

    @Override
    public PageUtils getPublishInfoList(PublishInfoListDTO publishInfoListDTO) {
        // 搜索条件有cityId，先判断cityId的层级
        // 如果cityId是第三层数据，则直接进行搜索，否则需要获取该该层级下的所有cityId，根据所有的cityId进行搜索
        List<Long> cityIds = houseCityService.getRelatedCityIdsById(publishInfoListDTO.getCityId());
        if (cityIds != null && cityIds.size() > 1) {
            // 如果选中的city不是第三层数据
            // 获取选中的城市以及其下属的所有城市id
            cityIds = houseCityService.listByIds(cityIds).stream()
                    .filter(houseCityEntity -> Objects.equals(houseCityEntity.getLevel(), CityLevelConstant.THIRD))
                    .map(HouseCityEntity::getId).collect(Collectors.toList());
            publishInfoListDTO.setCityId(null);
            publishInfoListDTO.setCityIdList(cityIds);
        }
        // 获取数据，当前数据没有房源所在道路名称，只有所在道路id，也没有所有图片数据，只有默认图片url
        if(publishInfoListDTO.getRoadId()!=null && publishInfoListDTO.getRoadId()>0){
            // 搜索条件有道路id，获取其下的所有住宅区
            Long roadId = publishInfoListDTO.getRoadId();
            List<Long> areaIdList = houseAreaService.list(new LambdaQueryWrapper<HouseAreaEntity>()
                    .select(HouseAreaEntity::getId)
                    .eq(HouseAreaEntity::getSuperiorId, roadId)
            ).stream().map(HouseAreaEntity::getId).collect(Collectors.toList());
            if (areaIdList.size()>0){
                publishInfoListDTO.setRoadId(null);
                publishInfoListDTO.setAreaIdList(areaIdList);
            }else {
                publishInfoListDTO.setAreaId(-1L);
            }
        }
        // 先获取当前条件下的总记录数
        long totalCount = houseDao.countPublishList(publishInfoListDTO);
        // 再获取当前条件下的实际记录
        List<PublishBaseInfoVO> publishBaseInfoVOList = houseDao.getPublishInfoList(publishInfoListDTO);
        // 为每条记录填充相关数据（图片，道路id）
        setPublishBaseOtherInfo(publishBaseInfoVOList);
        return new PageUtils(publishBaseInfoVOList, Math.toIntExact(totalCount), PageConstant.LIMIT, publishInfoListDTO.getCurrent());
    }

    @Override
    public List<PublishBaseInfoVO> getBrowsePublishInfoList(PublishInfoListDTO publishInfoListDTO) {
        List<PublishBaseInfoVO> publishInfoList = houseDao.getMyBrowseList(publishInfoListDTO);
        setPublishBaseOtherInfo(publishInfoList);
        return publishInfoList;
    }

    public PublishInfoVO getPublishInfoFromDataBase(Long houseBaseInfoId) {
        PublishInfoVO publishInfoVO = new PublishInfoVO();
        publishInfoVO.setHouseBaseInfoId(houseBaseInfoId);
        // 获取基本信息
        CompletableFuture<Void> baseInfoCompletableFuture = CompletableFuture.runAsync(() -> {
            HouseBaseInfoEntity baseInfo = baseInfoService.getById(houseBaseInfoId);
            publishInfoVO.setBaseInfo(new PublishInfoVO.BaseInfoVO(baseInfo));
            // 获取房源所在道路id
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
            // 获取该房源所拥有的设施id
            List<Long> baseFacilitiesIds = publishInfoVO.getDetailedInfo().getBaseFacilitiesIds();
            List<BaseFacilitiesEntity> baseFacilitiesEntities = baseFacilitiesService.listByIds(baseFacilitiesIds);
            // 填充icon
            List<PublishInfoVO.Facilities> facilities = new ArrayList<>();
            baseFacilitiesEntities.forEach(baseFacilities -> facilities.add(new PublishInfoVO.Facilities(baseFacilities.getName(), baseFacilities.getIconUrl())));
            publishInfoVO.setFacilitiesList(facilities);
        }, executor);
        // 获取图片信息
        CompletableFuture<Void> imageListCompletableFuture = CompletableFuture.runAsync(() -> {
            List<HouseImageEntity> imageList = imageService.list(new LambdaQueryWrapper<HouseImageEntity>().eq(HouseImageEntity::getBaseInfoId, houseBaseInfoId));
            List<PublishInfoVO.ImageInfoVO> imageInfoVOList = new ArrayList<>();
            List<String> previewSrcList = new ArrayList<>();
            imageList.forEach(imageEntity -> imageInfoVOList.add(new PublishInfoVO.ImageInfoVO(imageEntity)));
            imageList.forEach(imageEntity -> previewSrcList.add(imageEntity.getUrl()));
            publishInfoVO.setImageInfoList(imageInfoVOList);
            publishInfoVO.setPreviewSrcList(previewSrcList);
        }, executor);
        // 获取联系人信息
        CompletableFuture<Void> contactInfoCompletableFuture = CompletableFuture.runAsync(() -> {
            HouseContactInfoEntity contactInfo = contactInfoService.getOne(new LambdaQueryWrapper<HouseContactInfoEntity>().eq(HouseContactInfoEntity::getBaseInfoId, houseBaseInfoId));
            HouseStateEntity houseStateEntity = stateService.list(new LambdaQueryWrapper<HouseStateEntity>()
                    .select(HouseStateEntity::getPublisherId)
                    .eq(HouseStateEntity::getBaseInfoId, houseBaseInfoId)
                    .last("limit 1")
            ).get(0);
            R r = userFeignClient.info(houseStateEntity.getPublisherId());
            UserBaseInfoEntity userBaseInfo = r.getData("userBaseInfo", new TypeReference<UserBaseInfoEntity>() {});
            PublishInfoVO.ContactInfoVO contactInfoVO = new PublishInfoVO.ContactInfoVO(contactInfo);
            contactInfoVO.setHeadPortraitUrl(userBaseInfo.getHeadPortraitUrl());
            publishInfoVO.setContactInfo(contactInfoVO);
        }, executor);

        CompletableFuture.allOf(baseInfoCompletableFuture, rentInfoCompletableFuture, detailedInfoCompletableFuture, imageListCompletableFuture, contactInfoCompletableFuture).join();
        return publishInfoVO;

    }

    @Override
    public PublishInfoVO getUpdatePublishInfo(Long houseBaseInfoId) {
        PublishInfoVO publishInfoVO = getPublishInfoFromDataBase(houseBaseInfoId);
        // 获取相关的城市id
        List<Long> relatedIds = houseCityService.getRelatedSuperiorIdsById(publishInfoVO.getBaseInfo().getCityId());
        publishInfoVO.setCityWithAreaVO(baseInfoService.getCityWithAreaInfo(relatedIds.get(relatedIds.size() - 1)));
        return publishInfoVO;
    }

    @Override
    public PublishInfoVO getPublishInfo(Long houseBaseInfoId) {
        PublishInfoVO publishInfoVO = getPublishInfoFromDataBase(houseBaseInfoId);
        CompletableFuture<Void> baseInfoCompletableFuture = CompletableFuture.runAsync(() -> {
            PublishInfoVO.BaseInfoVO baseInfo = publishInfoVO.getBaseInfo();
            // 为基础信息填充所需的数据，即从id到name
            Long cityId = baseInfo.getCityId();
            baseInfo.setCityName(houseCityService.getOne(new LambdaQueryWrapper<HouseCityEntity>().eq(HouseCityEntity::getId, cityId).select(HouseCityEntity::getName)).getName());
            Long roadId = baseInfo.getRoadId();
            baseInfo.setRoadName(houseAreaService.getOne(new LambdaQueryWrapper<HouseAreaEntity>().eq(HouseAreaEntity::getId, roadId).select(HouseAreaEntity::getName)).getName());
            Long areaId = baseInfo.getAreaId();
            baseInfo.setAreaName(houseAreaService.getOne(new LambdaQueryWrapper<HouseAreaEntity>().eq(HouseAreaEntity::getId, areaId).select(HouseAreaEntity::getName)).getName());
            Long orientationId = baseInfo.getOrientationId();
            baseInfo.setOrientation(baseOrientationService.getOne(new LambdaQueryWrapper<BaseOrientationEntity>().eq(BaseOrientationEntity::getId, orientationId).select(BaseOrientationEntity::getOrientation)).getOrientation());
        }, executor);
        CompletableFuture<Void> rentInfoCompletableFuture = CompletableFuture.runAsync(() -> {
            PublishInfoVO.RentInfoVO rentInfo = publishInfoVO.getRentInfo();
            //为租金信息填充所需数据
            List<Long> rentContentIds = rentInfo.getRentContentIds();
            rentInfo.setRentContentList(baseRentContentService
                    .list(new LambdaQueryWrapper<BaseRentContentEntity>()
                            .in(BaseRentContentEntity::getId, rentContentIds)
                            .select(BaseRentContentEntity::getRentContentName)
                    ).stream().map(BaseRentContentEntity::getRentContentName).collect(Collectors.toList())
            );
        }, executor);
        CompletableFuture<Void> detailedCompletableFuture = CompletableFuture.runAsync(() -> {
            PublishInfoVO.DetailedInfoVO detailedInfo = publishInfoVO.getDetailedInfo();
            // 为详细详细填充所需数据
            List<Long> baseFacilitiesIds = detailedInfo.getBaseFacilitiesIds();
            if (baseFacilitiesIds.size() > 0) {
                detailedInfo.setBaseFacilitiesList(baseFacilitiesService
                        .list(new LambdaQueryWrapper<BaseFacilitiesEntity>()
                                .in(BaseFacilitiesEntity::getId, baseFacilitiesIds)
                                .select(BaseFacilitiesEntity::getName)
                        ).stream().map(BaseFacilitiesEntity::getName).collect(Collectors.toList())
                );
            }
            List<Long> baseHighlightIds = detailedInfo.getBaseHighlightIds();
            if (baseHighlightIds.size() > 0) {
                detailedInfo.setBaseHighlightList(baseHighlightService
                        .list(new LambdaQueryWrapper<BaseHighlightEntity>()
                                .in(BaseHighlightEntity::getId, baseHighlightIds)
                                .select(BaseHighlightEntity::getName)
                        ).stream().map(BaseHighlightEntity::getName).collect(Collectors.toList())
                );
            }
            List<Long> baseRentalRequirementIds = detailedInfo.getBaseRentalRequirementIds();
            if (baseRentalRequirementIds.size() > 0) {
                detailedInfo.setBaseRentalRequirementList(baseRentalRequirementsService
                        .list(new LambdaQueryWrapper<BaseRentalRequirementsEntity>()
                                .in(BaseRentalRequirementsEntity::getId, baseRentalRequirementIds)
                                .select(BaseRentalRequirementsEntity::getName)
                        ).stream().map(BaseRentalRequirementsEntity::getName).collect(Collectors.toList())
                );
            }
        }, executor);
        CompletableFuture.allOf(baseInfoCompletableFuture, rentInfoCompletableFuture, detailedCompletableFuture).join();
        CompletableFuture.runAsync(() -> promotionService.incrVisitorNumber(houseBaseInfoId), executor);
        return publishInfoVO;
    }

    @Override
    public boolean changePublishState(Long houseBaseInfoId, Integer state) {
        if (state >= 0 && state <= 4) {
            return stateService.update(new LambdaUpdateWrapper<HouseStateEntity>()
                    .eq(HouseStateEntity::getBaseInfoId, houseBaseInfoId)
                    .set(HouseStateEntity::getHousePublishState, state));
        }
        return false;
    }

    @Override
    public List<RecommendInfoVO> getRecommendList(RecommendInfoListDTO recommendInfoListDTO) {
        if (recommendInfoListDTO.getChildrenCityId() != null) {
            recommendInfoListDTO.setCityId(null);
            recommendInfoListDTO.setChildrenCityIds(null);
        } else {
            List<Long> childrenCityIds = houseCityService.getRelatedCityIdsById(recommendInfoListDTO.getCityId());
            recommendInfoListDTO.setChildrenCityIds(childrenCityIds);
        }
        if (recommendInfoListDTO.getRoadId() != null) {
            Long roadId = recommendInfoListDTO.getRoadId();
            List<Long> areaIds = houseAreaService.list(new LambdaQueryWrapper<HouseAreaEntity>()
                    .select(HouseAreaEntity::getId)
                    .eq(HouseAreaEntity::getSuperiorId, roadId)
            ).stream().map(HouseAreaEntity::getId).collect(Collectors.toList());
            if (areaIds.size()>0){
                recommendInfoListDTO.setAreaIds(areaIds);
            }else {
                recommendInfoListDTO.setIsEmpty(0L);
            }
        }
        List<RecommendInfoVO> recommendInfoVOList = houseDao.getRecommendInfoList(recommendInfoListDTO);
        getRoadNameForRecommendInfoList(recommendInfoVOList);
        // TODO 更新-推荐次数 可用推荐次数减少一次
        return recommendInfoVOList;
    }

    @Override
    public List<RecommendInfoVO> getRecommendList(Long baseInfoId) {
        Long childrenCityId = baseInfoService.getById(baseInfoId).getCityId();
        RecommendInfoListDTO recommendInfoListDTO = new RecommendInfoListDTO();
        recommendInfoListDTO.setCityId(childrenCityId);
        List<RecommendInfoVO> recommendInfoList = houseDao.getRecommendInfoList(recommendInfoListDTO);
        getRoadNameForRecommendInfoList(recommendInfoList);
        // TODO 更新-推荐次数 可用推荐次数减少一次
        return recommendInfoList;
    }

    private void getRoadNameForRecommendInfoList(List<RecommendInfoVO> recommendInfoList) {
        recommendInfoList = recommendInfoList.stream().peek(recommendInfoVO -> {
            Long roadId = houseAreaService.getRelatedSuperiorIdById(recommendInfoVO.getAreaId());
            String roadName = houseAreaService.getById(roadId).getName();
            recommendInfoVO.setRoadName(roadName);
        }).collect(Collectors.toList());
    }


}
