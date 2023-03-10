package com.sakanal.service.vo;

import com.sakanal.service.entity.house.HouseAreaEntity;
import com.sakanal.service.entity.house.HouseCityEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PublishBaseInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CityVO> cityList;
    private List<AreaVO> roadList;
    private List<AreaVO> areaList;

    public PublishBaseInfoVO(List<HouseCityEntity> cityList, List<HouseAreaEntity> roadList, List<HouseAreaEntity> areaList) {
        this.cityList = cityList.stream().map(CityVO::new).collect(Collectors.toList());
        this.roadList = roadList.stream().map(AreaVO::new).collect(Collectors.toList());
        this.areaList = areaList.stream().map(AreaVO::new).collect(Collectors.toList());
    }
}

