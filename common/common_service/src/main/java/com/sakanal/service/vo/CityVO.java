package com.sakanal.service.vo;

import com.sakanal.service.entity.house.HouseCityEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CityVO {
    private Long id;
    private String name;
    private Long superiorId;
    private boolean hasChildren;
    private List<CityVO> childrenList;

    public CityVO(HouseCityEntity houseCityEntity) {
        this.id = houseCityEntity.getId();
        this.name = houseCityEntity.getName();
        this.superiorId = houseCityEntity.getSuperiorId();
        this.hasChildren = houseCityEntity.isHasChildren();
        if (this.hasChildren) {
            List<HouseCityEntity> childrenList = houseCityEntity.getChildrenList();
            if (childrenList != null && childrenList.size() > 0) {
                this.childrenList = childrenList.stream().map(CityVO::new).collect(Collectors.toList());
            }
        } else {
            this.childrenList = null;
        }
    }
}
