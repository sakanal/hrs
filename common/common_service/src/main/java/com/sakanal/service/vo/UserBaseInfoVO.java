package com.sakanal.service.vo;

import com.sakanal.service.entity.user.UserBaseInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBaseInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String userName;
    private String nickName;
    private String headPortraitUrl;
    private Long phone;
    private String email;

    public UserBaseInfoVO(UserBaseInfoEntity userBaseInfoEntity) {
        BeanUtils.copyProperties(userBaseInfoEntity, this);
    }
}
