package com.sakanal.service.entity.house;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 房源推广数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-03-22 16:19:43
 */
@Data
@TableName("house_promotion")
public class HousePromotionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 房源基础信息id
     */
    @TableId
    private Long baseInfoId;
    /**
     * 浏览人数
     */
    private Integer visitorNumber;
    /**
     * 推广次数
     */
    private Integer promotionNumber;
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
