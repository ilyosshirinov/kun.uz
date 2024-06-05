package com.example.service;

import com.example.dto.article.ArticleCreateDto;
import com.example.dto.article.ArticleDto;
import com.example.dto.category.CategoryDto;
import com.example.dto.profile.ProfileDto;
import com.example.dto.region.RegionDto;
import com.example.entity.*;
import com.example.exp.AppBadException;
import com.example.repository.ArticleRepository;
import com.example.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ArticleTypesService articleTypesService;

    public ArticleDto createArticleService(ArticleCreateDto createDto) {

        ProfileEntity moderator = SecurityUtil.getProfile();

        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(createDto.getTitle());
        entity.setDescription(createDto.getDescription());
        entity.setContent(createDto.getContent());
        entity.setImageId(createDto.getImageId());
        entity.setRegionId(createDto.getRegionId());
        entity.setCategoryId(createDto.getCategoryId());
        entity.setModeratorId(moderator.getId());
//        entity.setStatus(ProfileRole.ROLE_PUBLISHER);

        articleRepository.save(entity);
        articleTypesService.create(entity.getId(), createDto.getTypesList());

        return toArticleDto(entity);
    }


//    public Boolean updateArticleService(Integer imageId, ArticleCreateDto createDto) {
//        List<ArticleEntity> entity = articleRepository.findByImageId(imageId);
//        if (entity.isEmpty()) {
//            throw new AppBadException(imageId + " topilmadi");
//        }
//        ArticleEntity articleEntity = new ArticleEntity();
//        articleEntity.setTitle(createDto.getTitle());
//        articleEntity.setDescription(createDto.getDescription());
//        articleEntity.setContent(createDto.getContent());
//        articleEntity.setImageId(createDto.getImageId());
//
////        RegionEntity regionEntity = new RegionEntity();
////        RegionDto regionDto = new RegionDto();
////        regionEntity.setId(regionDto.getId());
////        articleEntity.setRegionId(regionEntity);
////
////        CategoryEntity categoryEntity = new CategoryEntity();
////        CategoryDto categoryDto = new CategoryDto();
////        categoryEntity.setId(categoryDto.getId());
////        articleEntity.setCategoryId(categoryEntity);
//
//        articleRepository.save(articleEntity);
//        return true;
//
//    }
//
//    public String deleteArticleService(String id) {
//        ArticleEntity entity = articleRepository.getByIdArticle(id);
//        if (entity != null) {
//            articleRepository.delete(entity);
//            return id + " O'chirildi";
//        }
//        return "Siz izlagan " + id + " topilmadi";
//    }
//
//    public List<ArticleDto> getAll() {
//        Iterable<ArticleEntity> entity = articleRepository.findAll();
//        List<ArticleDto> list = new ArrayList<>();
//        for (ArticleEntity articleEntity : entity) {
//            list.add(toArticleDto(articleEntity));
//        }
//        return list;
//    }

    // TODO               METHOD
    public static ArticleDto toArticleDto(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setViewCount(entity.getViewCount());
        dto.setImageId(entity.getImageId());
        dto.setCreateDate(entity.getCreatedDate());
        dto.setPublishedDate(entity.getPublishedDate());

        RegionDto regionDto = new RegionDto();
        regionDto.setId(entity.getRegion().getId());
        dto.setRegion(regionDto);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(entity.getCategoryId());
        dto.setCategory(categoryDto);

        ProfileDto profileDto = new ProfileDto();
        ProfileEntity profileEntity = new ProfileEntity();
        profileDto.setId(profileEntity.getId());
        dto.setModerator(profileDto);
        dto.setPublisher(profileDto);

        dto.setStatus(entity.getStatus());
        dto.setVisible(entity.getVisible());

        return dto;
    }


//    public static ArticleDto toArticleDto(ArticleEntity entity) {
//        ArticleDto dto = new ArticleDto();
//        dto.setId(entity.getId());
//        dto.setTitle(entity.getTitle());
//        dto.setDescription(entity.getDescription());
//        dto.setContent(entity.getContent());
//        dto.setSharedCount(entity.getSharedCount());
//        dto.setImageId(entity.getImageId());
//        dto.setRegionId(entity.getRegionId());
//        dto.setCategoryId(entity.getCategoryId());
//        dto.setModeratorId(entity.getModeratorId());
//        dto.setPublisherId(entity.getPublisherId());
//        dto.setStatus(entity.getStatus());
//        dto.setCreatedDate(entity.getCreatedDate());
//        dto.setPublishedDate(entity.getPublishedDate());
//        dto.setVisible(entity.getVisible());
//        dto.setViewCount(entity.getViewCount());
//        dto.setArticleType(entity.getArticleType());
////        dto.setJwt(JWTUtil.encodeArticle(entity.getId(), entity.getStatus()));
//        return dto;
//    }


}
