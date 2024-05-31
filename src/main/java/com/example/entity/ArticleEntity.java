package com.example.entity;

import com.example.enums.ArticleStatus;
import com.example.enums.ArticleType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount;

    @Column(name = "image_id")
    private Integer imageId;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "moderator_id")
    private Integer moderatorId;

    @Column(name = "publisher_id")
    private Integer publisherId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus status = ArticleStatus.NOT_PUBLISHED;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "published_date")
    private LocalDateTime publishedDate = LocalDateTime.now();

    @Column(name = "vicible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "view_count")
    private Integer viewCount;


    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ArticleType articleType = ArticleType.NEWS;


}
