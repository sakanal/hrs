package com.sakanal.service.vo;

import com.sakanal.service.entity.promotion.HousePromotionOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private Long promotionId;
    private String promotionName;
    private Long baseInfoId;
    private String baseInfoTitle;
    private Long number;
    private BigDecimal totalMoney;
    private Integer showState;
    private Date createdTime;
    private Date modifyTime;

    public PromotionOrderVO(HousePromotionOrderEntity housePromotionOrderEntity) {
        this.id=housePromotionOrderEntity.getId();
        this.userId=housePromotionOrderEntity.getUserId();
        this.promotionId=housePromotionOrderEntity.getPromotionId();
        this.baseInfoId=housePromotionOrderEntity.getBaseInfoId();
        this.number=housePromotionOrderEntity.getNumber();
        this.totalMoney=housePromotionOrderEntity.getTotalMoney();
        this.showState=housePromotionOrderEntity.getShowState();
        this.createdTime=housePromotionOrderEntity.getCreatedTime();
        this.modifyTime=housePromotionOrderEntity.getModifyTime();
    }
}
