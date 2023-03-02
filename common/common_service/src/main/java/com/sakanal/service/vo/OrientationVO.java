package com.sakanal.service.vo;

import com.sakanal.service.entity.house.BaseOrientationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrientationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String orientation;

    public OrientationVO(BaseOrientationEntity orientationEntity) {
        BeanUtils.copyProperties(orientationEntity,this);
    }
}
