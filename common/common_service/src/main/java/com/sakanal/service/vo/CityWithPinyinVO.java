package com.sakanal.service.vo;

import com.sakanal.service.entity.house.HouseCityEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityWithPinyinVO implements Serializable, Comparable<CityWithPinyinVO> {
    private static final long serialVersionUID = 1L;

    private char pinyin;
    private List<City> cityList;

    public CityWithPinyinVO(List<HouseCityEntity> houseCityEntityList, char pinyin) {
        this.pinyin = pinyin;
        this.cityList = houseCityEntityList.stream().map(City::new).collect(Collectors.toList());
    }

    @Override
    public int compareTo(@NotNull CityWithPinyinVO other) {
        return this.pinyin - other.pinyin;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class City {
    private Long id;
    private String name;
    private Long superiorId;
    private boolean hasChildren;
    private List<City> childrenList;

    public City(HouseCityEntity houseCityEntity) {
        this.id = houseCityEntity.getId();
        this.name = houseCityEntity.getName();
        this.superiorId = houseCityEntity.getSuperiorId();
        this.hasChildren = houseCityEntity.isHasChildren();
        if (this.hasChildren) {
            List<HouseCityEntity> childrenList = houseCityEntity.getChildrenList();
            if (childrenList!=null && childrenList.size()>0){
                this.childrenList = childrenList.stream().map(City::new).collect(Collectors.toList());
            }
        } else {
            this.childrenList = null;
        }
    }
}
