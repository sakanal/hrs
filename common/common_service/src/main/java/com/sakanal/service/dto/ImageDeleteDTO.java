package com.sakanal.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDeleteDTO {
    private static final long serialVersionUID = 1L;

    private String name;
    private String url;
}
