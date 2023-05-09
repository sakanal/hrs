package com.sakanal.service.entity.house;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 房源联系电话表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
@Data
@TableName("house_contact_info")
public class HouseContactInfoEntity implements Serializable {
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
     * 发布人昵称
     */
    private String publisherName;
    /**
     * 发布人身份 0-个人房东 1-个人转租 2-职业房东
     */
    private Integer publisherIdentity;
    /**
     * 联系人电话
     */
    private Long contactPhone;
    /**
     * 接听时段_start
     */
    @JsonFormat(pattern = "HH:mm")
    private Date answerPeriodTimeStart;
    /**
     * 接听时段_end
     */
    @JsonFormat(pattern = "HH:mm")
    private Date answerPeriodTimeEnd;
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
