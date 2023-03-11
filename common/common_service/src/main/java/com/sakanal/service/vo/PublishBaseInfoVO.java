package com.sakanal.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishBaseInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long baseInfoId;
    // 基础信息表
    private String houseTitle;
    private Integer roomNumber;
    private Integer hallNumber;
    private Integer cloakroomNumber;
    private BigDecimal areaCovered;

    // 租金信息表
    private BigDecimal monthlyRent;

    // 图片信息表
    private String url;
    private List<String> imageList;

    // 联系人信息表
    private String publisherName;
    private Integer publisherIdentity;

    // 其他数据
    private Long roadId;
    private String roadName;
    private Long areaId;
    private String areaName;

}
