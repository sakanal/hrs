package com.sakanal.service.entity.house;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 房屋基础信息表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
@Data
@TableName("house_base_info")
public class HouseBaseInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 城市id
     */
    private Long cityId;
    /**
     * 区域id
     */
    private Long areaId;
    /**
     * 类别id
     */
    private Long categoryId;
    /**
     * 房源名称
     */
    private String name;
    /**
     * 房源有多少可用卧室
     */
    private Integer roomNumber;
    /**
     * 房源有多少可用厅室
     */
    private Integer hallNumber;
    /**
     * 房源有多少可用卫生间
     */
    private Integer cloakroomNumber;
    /**
     * 占地面积
     */
    private BigDecimal areaCovered;
    /**
     * 总层数
     */
    private Integer totalFloor;
    /**
     * 所在层数
     */
    private Integer floor;
    /**
     * 是否有电梯 0-没有电梯 1-有电梯
     */
    private Integer hasElevator;
    /**
     * 是否有车位 0-没有车位 1-有车位
     */
    private Integer hasParkingPlace;
    /**
     * 房屋朝向id
     */
    private Long orientationId;
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
