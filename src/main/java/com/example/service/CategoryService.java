package com.example.service;

import com.example.dto.category.CategoryCreateDto;
import com.example.dto.category.CategoryDto;
import com.example.entity.CategoryEntity;
import com.example.enums.LanguageEnum;
import com.example.exp.AppBadException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDto createCategoryService(CategoryCreateDto categoryCreateDto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(categoryCreateDto.getOrderNumber());
        entity.setNameUz(categoryCreateDto.getNameUz());
        entity.setNameRu(categoryCreateDto.getNameRu());
        entity.setNameEn(categoryCreateDto.getNameEn());
        categoryRepository.save(entity);
        return toCategoryDto(entity);
    }

    public CategoryDto updateByIdCategoryService(Integer id, CategoryCreateDto categoryCreateDto) {
        CategoryEntity entity = get(id);
        entity.setOrderNumber(categoryCreateDto.getOrderNumber());
        entity.setNameUz(categoryCreateDto.getNameUz());
        entity.setNameRu(categoryCreateDto.getNameRu());
        entity.setNameEn(categoryCreateDto.getNameEn());
        categoryRepository.save(entity);
        return toCategoryDto(entity);
    }

    public Boolean deleteByIdCategoryService(Integer id) {
        CategoryEntity entity = get(id);
        categoryRepository.delete(entity);
        return true;
    }

    public List<CategoryDto> allCategoryService() {
        List<CategoryEntity> entity = categoryRepository.findAllByOrderByOrderNumberAsc();
        List<CategoryDto> list = new ArrayList<>();
        for (CategoryEntity categoryEntity : entity) {
            list.add(toCategoryDto(categoryEntity));
        }
        return list;
    }

    public List<CategoryDto> langCategoryService(LanguageEnum lang) {
        List<CategoryEntity> entity = categoryRepository.findAllByVisibleTrueOrderByOrderNumberDesc();
        List<CategoryDto> list = new ArrayList<>();
        for (CategoryEntity categoryEntity : entity) {
            CategoryDto dto = new CategoryDto();
            dto.setId(categoryEntity.getId());
            dto.setOrderNumber(categoryEntity.getOrderNumber());
            switch (lang) {
                case UZ -> dto.setNameUz(categoryEntity.getNameUz());
                case RU -> dto.setNameRu(categoryEntity.getNameRu());
                case EN -> dto.setNameEn(categoryEntity.getNameEn());
            }
            list.add(dto);
        }
        return list;
    }

    public List<CategoryDto> langCategoryService2(LanguageEnum lang) {
        List<CategoryEntity> categoryMappers = categoryRepository.getByLang(lang.name());
        List<CategoryDto> list = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryMappers) {
            CategoryDto dto = new CategoryDto();
            dto.setId(categoryEntity.getId());
            switch (lang) {
                case UZ -> dto.setNameUz(categoryEntity.getNameUz());
                case RU -> dto.setNameRu(categoryEntity.getNameRu());
                case EN -> dto.setNameEn(categoryEntity.getNameEn());
            }
            dto.setOrderNumber(categoryEntity.getOrderNumber());
            list.add(dto);
        }
        return list;
    }


    // TODO            METHOD

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Types not found");
        });
    }

    public static CategoryDto toCategoryDto(CategoryEntity entity) {
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


}
