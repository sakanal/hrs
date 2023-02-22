package com.sakanal.house.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 房源详细介绍表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
@Data
@TableName("house_detailed_info")
public class HouseDetailedInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 房源基础信息id
     */
    private Long baseInfoId;
    /**
     * 最早入住时间
     */
    private Date earliestInTime;
    /**
     * 宜居人数
     */
    private Integer livablePeopleNumber;
    /**
     * 看房时间 0-仅周末 1-仅工作日 2-工作日晚上和周末 3-随时看房
     */
    private Integer lookingHouseTimeState;
    /**
     * 装修情况 0-毛胚 1-简单装修 2-精装修 3-豪华装修
     */
    private Integer decorationState;
    /**
     * 房屋设施id 使用"/"分隔
     */
    private String baseFacilitiesIds;
    /**
     * 房屋亮点id 使用"/"分隔
     */
    private String baseHighlightIds;
    /**
     * 房屋要求id 使用"/"分隔
     */
    private String baseRentalRequirementIds;
    /**
     * 房源详细描述
     */
    private String houseDetailedDescription;
    /**
     * 房源详细地址
     */
    private String houseDetailedAddress;
    /**
     * 是否删除 0-未删除 1-已删除
     */
    @TableLogic
    private Integer isDeleted;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}
