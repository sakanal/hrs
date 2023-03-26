package com.sakanal.service.dto;

import com.sakanal.base.constant.PageConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishInfoListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // 房源id
    private List<Long> baseInfoIdList;
    // 发布人id
    private Long publisherId;
    // 房源所在城市
    private Long cityId;
    // 房源所在城市id以及其下属的所有城市id，不会直接传递，后台获取
    private List<Long> cityIdList;
    // 房源所在道路
    private Long roadId;
    // 所在道路下的所有住宅区
    private List<Long> areaIdList;
    // 房源所在小区
    private Long areaId;
    // 房源租金范围
    private String rentQuery;
    private Long minRent;
    private Long maxRent;
    // 房源厅室数量
    private String hallQuery;
    private String roomQuery;
    // 房源朝向id
    private Long orientationId;
    // 房源装修情况
    private Integer decoration;
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

    public void setRentQuery(String rentQuery) {
        String[] split = rentQuery.split("_");
        if (split.length==1){
            this.minRent= Long.valueOf(split[0]);
        }else {
            if (StringUtils.hasText(split[0])){
                this.minRent = Long.valueOf(split[0]);
            }
            if (StringUtils.hasText(split[1])){
                this.maxRent = Long.valueOf(split[1]);
            }
        }
        this.rentQuery = rentQuery;
    }
}
