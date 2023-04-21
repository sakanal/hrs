package com.sakanal.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionOrderDTO {
    @NotNull
    private Long userId;
    @NotNull
    private Long promotionId;
    private BigDecimal totalMoney;
    private Long baseInfoId;
    @NotNull
    private Long number;

    public void setTotalMoney(BigDecimal price) {
        this.totalMoney=price.multiply(BigDecimal.valueOf(this.number));
    }
}
