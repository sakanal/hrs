package com.sakanal.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayDTO {
    private String out_trade_no; // 商户订单号 必填
    private String subject; // 订单名称 必填
    private String total_amount;  // 付款金额 必填
    private String body; // 商品描述 可空

    public PayDTO(Long orderId, String subject, BigDecimal totalMoney) {
        this.out_trade_no = String.valueOf(orderId);
        this.subject = subject;
        this.total_amount = String.valueOf(totalMoney);
    }
}

