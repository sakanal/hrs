package com.sakanal.service.vo;

import com.sakanal.service.entity.house.HouseCityEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityWithPinyinVO implements Serializable, Comparable<CityWithPinyinVO> {
    private static final long serialVersionUID = 1L;

    private char pinyin;
    private List<CityVO> cityList;

    public CityWithPinyinVO(List<HouseCityEntity> houseCityEntityList, char pinyin) {
        this.pinyin = pinyin;
        this.cityList = houseCityEntityList.stream().map(CityVO::new).collect(Collectors.toList());
    }

    @Override
    public int compareTo(@NotNull CityWithPinyinVO other) {
        return this.pinyin - other.pinyin;
    }
}
