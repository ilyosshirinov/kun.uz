package com.example.dto.article;

import com.example.enums.ArticleStatus;
import lombok.Data;

@Data
public class ArticleJwtDto {
    private String id;
    private ArticleStatus status;

    public ArticleJwtDto(String  id, ArticleStatus status) {
        this.id = id;
        this.status = status;
    }

    public ArticleJwtDto(String  id) {
        this.id = id;
    }
}
