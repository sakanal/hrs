package com.sakanal.service.dto;

import com.sakanal.service.entity.promotion.HousePromotionOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionNumberDTO {
    private Long userId;
    private Long baseInfoId;
    private Long promotionNumber;

    public PromotionNumberDTO(HousePromotionOrderEntity promotionOrder) {
        this.userId=promotionOrder.getUserId();
        this.baseInfoId=promotionOrder.getBaseInfoId();
    }
}
