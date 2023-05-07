package com.sakanal.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailCodeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String subject;
    private String text;
    private String email;
    private String code;
}
