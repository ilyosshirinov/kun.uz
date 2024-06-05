package com.example.enums;

import com.example.entity.ArticleEntity;
import com.example.entity.TypesEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "article_type")
public class ArticleTypesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "types_id")
    private Integer typesId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "types_id", insertable = false, updatable = false)
    private TypesEntity types;

}
