package com.sakanal.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakanal.service.entity.house.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long houseBaseInfoId;
    private PublishInfoVO.BaseInfoVO baseInfo;
    private PublishInfoVO.RentInfoVO rentInfo;
    private PublishInfoVO.DetailedInfoVO detailedInfo;
    public List<Facilities> facilitiesList;
    private List<PublishInfoVO.ImageInfoVO> imageInfoList;
    private List<String> previewSrcList;
    private PublishInfoVO.ContactInfoVO contactInfo;
    private CityWithAreaVO cityWithAreaVO;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BaseInfoVO {
        private Long cityId;
        private String cityName;
        private Long roadId;
        private String roadName;
        private Long areaId;
        private String areaName;
        private String houseTitle;
        private String name;
        private Integer roomNumber;
        private Integer hallNumber;
        private Integer cloakroomNumber;
        private BigDecimal areaCovered;
        private Integer totalFloor;
        private Integer floor;
        private Boolean hasElevator;
        private Boolean hasParkingPlace;
        private Long orientationId;
        private String orientation;

        public void setName(String name) {
            this.name = name;
            this.houseTitle = name;
        }

        public BaseInfoVO(HouseBaseInfoEntity houseBaseInfo) {
            BeanUtils.copyProperties(houseBaseInfo, this);
            this.hasElevator = houseBaseInfo.getHasElevator() == 1;
            this.hasParkingPlace = houseBaseInfo.getHasParkingPlace() == 1;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RentInfoVO {
        private BigDecimal monthlyRent;
        private Integer payNumber;
        private Integer mortgageNumber;
        private List<Long> rentContentIds;
        private List<String> rentContentList;

        public RentInfoVO(HouseRentInfoEntity rentInfo) {
            BeanUtils.copyProperties(rentInfo, this);
            this.rentContentIds = new ArrayList<>();
            String[] split = rentInfo.getRentContentIds().split("/");
            if (split.length>0 && StringUtils.isNotBlank(split[0])){
                for (String s : split) {
                    this.rentContentIds.add(Long.valueOf(s));
                }
            }
        }
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Facilities{
        private String name;
        private String icon;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailedInfoVO {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date earliestInTime;
        private Integer livablePeopleNumber;
        private Integer lookingHouseTimeState;
        private Integer decorationState;
        // id
        private List<Long> baseFacilitiesIds;
        // 名字
        private List<String> baseFacilitiesList;
        private List<Long> baseHighlightIds;
        private List<String> baseHighlightList;
        private List<Long> baseRentalRequirementIds;
        private List<String> baseRentalRequirementList;
        private String houseDetailedDescription;

        public DetailedInfoVO(HouseDetailedInfoEntity detailedInfo) {
            BeanUtils.copyProperties(detailedInfo, this);
            this.baseFacilitiesIds = new ArrayList<>();
            String[] split = detailedInfo.getBaseFacilitiesIds().split("/");
            if (split.length>0 && StringUtils.isNotBlank(split[0])){
                for (String facilitiesId : split) {
                    this.baseFacilitiesIds.add(Long.valueOf(facilitiesId));
                }
            }
            this.baseHighlightIds = new ArrayList<>();
            split = detailedInfo.getBaseHighlightIds().split("/");
            if (split.length>0 && StringUtils.isNotBlank(split[0])){
                for (String highlightId : split) {
                    this.baseHighlightIds.add(Long.valueOf(highlightId));
                }
            }
            this.baseRentalRequirementIds = new ArrayList<>();
            split = detailedInfo.getBaseRentalRequirementIds().split("/");
            if (split.length>0 && StringUtils.isNotBlank(split[0])){
                for (String rentalRequirementId : split) {
                    this.baseRentalRequirementIds.add(Long.valueOf(rentalRequirementId));
                }
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageInfoVO {
        private String name;
        private String url;
        private Integer sort;
        private Integer isDefaultImage;

        public ImageInfoVO(HouseImageEntity imageEntity) {
            BeanUtils.copyProperties(imageEntity, this);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContactInfoVO {
        private String publisherName;
        private Integer publisherIdentity;
        private Long contactPhone;
        @JsonFormat(pattern = "hh:mm")
        private Date answerPeriodTimeStart;
        @JsonFormat(pattern = "hh:mm")
        private Date answerPeriodTimeEnd;
        private String headPortraitUrl;

        public ContactInfoVO(HouseContactInfoEntity contactInfo) {
            BeanUtils.copyProperties(contactInfo, this);
        }
    }

}
