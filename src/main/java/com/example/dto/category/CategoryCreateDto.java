package com.example.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryCreateDto {
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
}
