package com.sakanal.service.dto;

import com.sakanal.base.constant.PageConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishInfoListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // 发布人id
    private Long publisherId;
    // 房源所在城市
    private Long cityId;
    private List<Long> cityIdList;
    // 房源所在小区
    private Long areaId;
    // 房源发布状态
    private Integer state;
    // 是否逻辑删除
    private Integer isDeleted;
    // 页码
    private Integer current = 1;
    private Integer offset = 0;
    private Integer limit = PageConstant.LIMIT;

    public void setCurrent(Integer current) {
        if (current != null && current > 0) {
            this.current = current;
            this.offset = (current - 1) * limit;
        }
    }
}
