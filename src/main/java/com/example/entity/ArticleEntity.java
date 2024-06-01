package com.example.entity;

import com.example.enums.ArticleStatus;
import com.example.enums.ArticleType;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
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

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private RegionEntity regionId;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity categoryId;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private ProfileEntity moderator;

    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private ProfileEntity publisher;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileRole status = ProfileRole.ROLE_PUBLISHER;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "published_date")
    private LocalDateTime publishedDate = LocalDateTime.now();

    @Column(name = "vicible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "view_count")
    private Integer viewCount;


    @OneToMany
    @JoinColumn(name = "articleTypes")
    private List<TypesEntity> articleType;


}
