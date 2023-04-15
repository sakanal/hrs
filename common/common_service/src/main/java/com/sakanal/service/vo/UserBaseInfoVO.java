package com.sakanal.service.vo;

import cn.hutool.core.util.DesensitizedUtil;
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
    private String phone;
    private String email;

    public UserBaseInfoVO(UserBaseInfoEntity userBaseInfoEntity) {
        BeanUtils.copyProperties(userBaseInfoEntity, this);
        this.email = DesensitizedUtil.email(userBaseInfoEntity.getEmail());
        this.phone = DesensitizedUtil.mobilePhone(String.valueOf(userBaseInfoEntity.getPhone()));
    }
}
