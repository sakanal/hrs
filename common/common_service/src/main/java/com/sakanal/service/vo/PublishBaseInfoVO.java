package com.sakanal.service.vo;

import com.sakanal.service.entity.house.HouseAreaEntity;
import com.sakanal.service.entity.house.HouseCityEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PublishBaseInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<City> cityList;
    private List<Area> roadList;
    private List<Area> areaList;

    public PublishBaseInfoVO(List<HouseCityEntity> cityList, List<HouseAreaEntity> roadList, List<HouseAreaEntity> areaList) {
        this.cityList = cityList.stream().map(City::new).collect(Collectors.toList());
        this.roadList = roadList.stream().map(Area::new).collect(Collectors.toList());
        this.areaList = areaList.stream().map(Area::new).collect(Collectors.toList());
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

@Data
@AllArgsConstructor
@NoArgsConstructor
class Area{
    private Long id;
    private Long cityId;
    private String name;
    private Long superiorId;
    private String areaAddress;
    private boolean hasChildren;
    private List<Area> childrenList;

    public Area(HouseAreaEntity houseAreaEntity){
        BeanUtils.copyProperties(houseAreaEntity,this);
        this.hasChildren = houseAreaEntity.isHasChildren();
        if (this.hasChildren){
            List<HouseAreaEntity> childrenList = houseAreaEntity.getChildrenList();
            if (childrenList!=null && childrenList.size()>0){
                this.childrenList = childrenList.stream().map(Area::new).collect(Collectors.toList());
            }
        }else {
            this.childrenList = null;
        }
    }
}
