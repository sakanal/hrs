package com.sakanal.service.vo;

import com.sakanal.service.entity.house.BaseFacilitiesEntity;
import com.sakanal.service.entity.house.BaseRentalRequirementsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalRequirementsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public RentalRequirementsVO(BaseRentalRequirementsEntity rentalRequirementsEntity) {
        BeanUtils.copyProperties(rentalRequirementsEntity,this);
    }
}
