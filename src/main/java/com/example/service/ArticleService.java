package com.example.service;

import com.example.dto.article.ArticleCreateDto;
import com.example.dto.article.ArticleDto;
import com.example.entity.ArticleEntity;
import com.example.exp.AppBadException;
import com.example.repository.ArticleRepository;
import com.example.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public ArticleDto createArticleService(ArticleCreateDto createDto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(createDto.getTitle());
        entity.setDescription(createDto.getDescription());
        entity.setContent(createDto.getContent());
        entity.setImageId(createDto.getImageId());
        entity.setRegionId(createDto.getRegionId());
        entity.setCategoryId(createDto.getCategoryId());
        articleRepository.save(entity);

        return toArticleDtoToken(entity);
    }

    public ArticleDto updateArticleService(Integer imageId, ArticleCreateDto createDto) {
        List<ArticleEntity> entity = articleRepository.findByImageId(imageId);
        if (entity.isEmpty()) {
            throw new AppBadException(imageId + " topilmadi");
        }
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(createDto.getTitle());
        articleEntity.setDescription(createDto.getDescription());
        articleEntity.setContent(createDto.getContent());
        articleEntity.setImageId(createDto.getImageId());
        articleEntity.setRegionId(createDto.getRegionId());
        articleEntity.setCategoryId(createDto.getCategoryId());
        articleEntity.setImageId(createDto.getImageId());
        articleRepository.save(articleEntity);
        return toArticleDto(articleEntity);

    }

    public String deleteArticleService(String id) {
        ArticleEntity entity = articleRepository.getByIdArticle(id);
        if (entity != null) {
            articleRepository.delete(entity);
            return id + " O'chirildi";
        }
        return "Siz izlagan " + id + " topilmadi";
    }

    // TODO               METHOD
    public static ArticleDto toArticleDtoToken(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegionId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setModeratorId(entity.getModeratorId());
        dto.setPublisherId(entity.getPublisherId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setVisible(entity.getVisible());
        dto.setViewCount(entity.getViewCount());
        dto.setArticleType(entity.getArticleType());
        dto.setJwt(JWTUtil.encodeArticle(entity.getId(), entity.getStatus()));
        return dto;
    }

    public static ArticleDto toArticleDto(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegionId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setModeratorId(entity.getModeratorId());
        dto.setPublisherId(entity.getPublisherId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setVisible(entity.getVisible());
        dto.setViewCount(entity.getViewCount());
        dto.setArticleType(entity.getArticleType());
//        dto.setJwt(JWTUtil.encodeArticle(entity.getId(), entity.getStatus()));
        return dto;
    }


}
