package com.sakanal.service.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class LoginSimpleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "用户名不能为空")
    private String userName;
    @NotNull(message = "密码不能为空")
    @Length(min = 6,max = 18,message = "密码长度应为6到18位")
    private String password;
}
