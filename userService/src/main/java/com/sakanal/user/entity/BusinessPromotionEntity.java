package com.sakanal.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家推广数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
@Data
@TableName("business_promotion")
public class BusinessPromotionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 商家的用户id
     */
    private Long userId;
    /**
     * 房源基本信息id
     */
    private Long baseInfoId;
    /**
     * 房源类别id
     */
    private Long categoryId;
    /**
     * 房源状态id
     */
    private Long stateId;
    /**
     * 推广次数
     */
    private Long promotionNumber;
    /**
     * 推广状态 0-审核中 1-推广中 2-结束推广
     */
    private Integer promotionState;
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
