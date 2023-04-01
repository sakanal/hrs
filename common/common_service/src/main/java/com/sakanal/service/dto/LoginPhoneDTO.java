package com.sakanal.service.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class LoginPhoneDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$")
    private String phone;
    @NotNull
    @Length(min = 6,max = 6)
    private String code;
}
