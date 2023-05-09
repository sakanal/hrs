package com.sakanal.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
// TODO jsr303校验
public class PublishInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发布时-发布人id
     * 修改时-基础信息id
     */
    private Long publishId;
    private BaseInfoDTO baseInfo;
    private RentInfoDTO rentInfo;
    private DetailedInfoDTO detailedInfo;
    private List<ImageInfoDTO> imageInfoList;
    private ContactInfoDTO contactInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BaseInfoDTO {
        private Long cityId;
        private Long areaId;
        //HouseBaseInfoEntity-name
        private String houseTitle;
        private String name;
        private Integer roomNumber;
        private Integer hallNumber;
        private Integer cloakroomNumber;
        private BigDecimal areaCovered;
        private Integer totalFloor;
        private Integer floor;
        private Integer hasElevator;
        private Integer hasParkingPlace;
        private Long orientationId;

        public void setHouseTitle(String houseTitle) {
            this.houseTitle = houseTitle;
            this.name = houseTitle;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RentInfoDTO {
        private BigDecimal monthlyRent;
        private Integer payNumber;
        private Integer mortgageNumber;
        private List<Long> rentContentIds;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailedInfoDTO {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date earliestInTime;
        private Integer livablePeopleNumber;
        private Integer lookingHouseStateTime;
        private Integer lookingHouseTimeState;
        private Integer decorationState;
        private List<Long> baseFacilitiesIds;
        private List<Long> baseHighlightIds;
        private List<Long> baseRentalRequirementIds;
        private String houseDetailedDescription;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageInfoDTO {
        private String name;
        private String url;
        private Integer sort;
        private Integer isDefaultImage;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContactInfoDTO {
        private String publisherName;
        private Integer publisherIdentity;
        private Long contactPhone;
        @JsonFormat(pattern = "hh:mm")
        private Date answerPeriodTimeStart;
        @JsonFormat(pattern = "hh:mm")
        private Date answerPeriodTimeEnd;
    }

}
