package com.sakanal.service.entity.house;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 房屋区域表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 21:55:47
 */
@Data
@TableName("house_area")
public class HouseAreaEntity implements Serializable {
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
     * 区域名称
     */
    private String name;
    /**
     * 区域级别 0-区 1-路 2-小区
     */
    private Integer level;
    /**
     * 父区域id
     */
    private Long superiorId;
    /**
     * 是否有子节点
     */
    @TableField(exist = false)
    private boolean hasChildren;
    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<HouseAreaEntity> ChildrenList;
    @TableField(exist = false)
    private List<HouseAreaEntity> Children;
    /**
     * 区域地址 省-市-区-路-号 使用“-”分隔
     */
    private String areaAddress;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否显示 0-不显示 1-显示
     */
    private Integer showState;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseAreaEntity that = (HouseAreaEntity) o;
        return Objects.equals(id, that.id) && cityId.equals(that.cityId) && Objects.equals(name, that.name) && level.equals(that.level) && superiorId.equals(that.superiorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cityId, name, level, superiorId);
    }
}
