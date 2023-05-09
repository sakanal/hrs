package com.sakanal.service.vo;

import com.sakanal.service.entity.house.BaseFacilitiesEntity;
import com.sakanal.service.entity.house.BaseHighlightEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilitiesVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Integer category;

    public FacilitiesVO(BaseFacilitiesEntity facilitiesEntity) {
        BeanUtils.copyProperties(facilitiesEntity,this);
    }
}
