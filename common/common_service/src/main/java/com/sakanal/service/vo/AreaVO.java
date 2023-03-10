package com.sakanal.service.vo;

import com.sakanal.service.entity.house.HouseAreaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
class AreaVO {
    private Long id;
    private Long cityId;
    private String name;
    private Long superiorId;
    private String areaAddress;
    private boolean hasChildren;
    private List<AreaVO> childrenList;

    public AreaVO(HouseAreaEntity houseAreaEntity) {
        BeanUtils.copyProperties(houseAreaEntity, this);
        this.hasChildren = houseAreaEntity.isHasChildren();
        if (this.hasChildren) {
            List<HouseAreaEntity> childrenList = houseAreaEntity.getChildrenList();
            if (childrenList != null && childrenList.size() > 0) {
                this.childrenList = childrenList.stream().map(AreaVO::new).collect(Collectors.toList());
            }
        } else {
            this.childrenList = null;
        }
    }
}
