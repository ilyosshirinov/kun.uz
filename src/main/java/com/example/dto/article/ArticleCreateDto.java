package com.example.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleCreateDto {
    private String title;
    private String description;
    private String content;
    private Integer imageId;
    private Integer regionId;
    private Integer categoryId;
    private Integer sharedCount;
//    private ArticleType articleType;
}
