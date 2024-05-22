package com.example.dto.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypesCreateDto {
    private String key;
    private String nameUz;
    private String nameRu;
    private String nameEn;
}
