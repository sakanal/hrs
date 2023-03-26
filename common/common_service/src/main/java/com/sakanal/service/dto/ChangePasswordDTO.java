package com.sakanal.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long userId;
    @NotNull(message = "旧密码不能为空")
    @Length(min = 6,max = 18,message = "密码长度应为6到18位")
    private String oldPassword;
    @NotNull(message = "新密码不能为空")
    @Length(min = 6,max = 18,message = "密码长度应为6到18位")
    private String newPassword;
    @NotNull(message = "确认密码不能为空")
    @Length(min = 6,max = 18,message = "密码长度应为6到18位")
    private String checkPassword;
}
