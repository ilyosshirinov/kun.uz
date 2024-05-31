package com.example.dto.article;

import com.example.enums.ArticleStatus;
import com.example.enums.ArticleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDto {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer imageId;
    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Integer viewCount;
    private ArticleType articleType;
    private String jwt;


}
