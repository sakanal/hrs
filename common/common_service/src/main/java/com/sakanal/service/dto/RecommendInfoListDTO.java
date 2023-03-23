package com.sakanal.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendInfoListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 1、只有cityId
     * 2、有cityId和childrenCityId，只使用childrenCityId
     * 3、有cityId和childrenCityId和roadId，使用childrenCityId和roadId
     */
    // 省级城市id
    // 如果没有携带childrenCityId，需要根据cityId获取其下的县级城市id
    @NotNull
    private Long cityId;
    // 县级城市id
    private Long childrenCityId;
    private List<Long> childrenCityIds;
    // 道路城市id
    private Long roadId;
    // 如果携带了roadId，需要根据roadId获取其下的所有areaId
    private List<Long> areaIds;

    private Long isEmpty;
}
