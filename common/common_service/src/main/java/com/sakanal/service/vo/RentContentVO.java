package com.sakanal.service.vo;

import com.sakanal.service.entity.house.BaseOrientationEntity;
import com.sakanal.service.entity.house.BaseRentContentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentContentVO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String rentContentName;

    public RentContentVO(BaseRentContentEntity rentContentEntity) {
        BeanUtils.copyProperties(rentContentEntity,this);
    }
}
