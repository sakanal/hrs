package com.sakanal.service.entity.promotion;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakanal.base.constant.OrderStateConstant;
import com.sakanal.service.dto.PromotionOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 推广活动订单表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-04-16 10:41:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("house_promotion_order")
public class HousePromotionOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 推广id
     */
    private Long promotionId;
    /**
     * 房源基础信息id
     */
    private Long baseInfoId;
    /**
     * 购买数量
     */
    private Long number;
    /**
     * 总金额数
     */
    private BigDecimal totalMoney;
    /**
     * 订单状态 0-未支付 1-已支付 2-取消订单
     */
    private Integer showState;
    /**
     * 0-未删除 1-已删除
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

    public HousePromotionOrderEntity(PromotionOrderDTO promotionOrderDTO) {
        this.userId=promotionOrderDTO.getUserId();
        this.promotionId=promotionOrderDTO.getPromotionId();
        this.baseInfoId= promotionOrderDTO.getBaseInfoId();
        this.totalMoney=promotionOrderDTO.getTotalMoney();
        this.number=promotionOrderDTO.getNumber();
        this.showState= OrderStateConstant.DEFAULT;
    }
}
