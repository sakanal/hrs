package com.sakanal.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String houseTitle;
    private Integer roomNumber;
    private Integer hallNumber;
    private Integer cloakroomNumber;
    private BigDecimal areaCovered;
    private Integer decorationState;
    private String childrenCityName;
    private String areaName;
    private Long areaId;
    private String roadName;
    private Long rent;
    private String url;
    private Integer promotionNumber;
    private Integer visitorNumber;
}
