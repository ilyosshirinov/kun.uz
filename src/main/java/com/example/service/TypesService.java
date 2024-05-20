package com.example.service;

import com.example.Mapper.TypesMapper;
import com.example.dto.TypesCreateDto;
import com.example.dto.TypesDto;
import com.example.entity.RegionEntity;
import com.example.entity.TypesEntity;
import com.example.enums.LanguageEnum;
import com.example.exp.AppBadException;
import com.example.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypesService {

    @Autowired
    private TypesRepository typesRepository;

    public TypesDto createTypesService(TypesCreateDto typesCreateDto) {
        TypesEntity entity = new TypesEntity();
        entity.setKey(typesCreateDto.getKey());
        entity.setNameUz(typesCreateDto.getNameUz());
        entity.setNameRu(typesCreateDto.getNameRu());
        entity.setNameEn(typesCreateDto.getNameEn());
        return toTypesDto(typesRepository.save(entity));
    }

    public TypesDto updateByIdTypesService(Integer id, TypesCreateDto typesCreateDto) {
        TypesEntity entity = get(id);
        entity.setKey(typesCreateDto.getKey());
        entity.setNameUz(typesCreateDto.getNameUz());
        entity.setNameRu(typesCreateDto.getNameRu());
        entity.setNameEn(typesCreateDto.getNameEn());
        return toTypesDto(typesRepository.save(entity));
    }

    public Boolean deleteByIdTypesService(Integer id) {
        TypesEntity entity = get(id);
        typesRepository.delete(entity);
        return true;
    }

    public List<TypesDto> allTypesService() {
        Iterable<TypesEntity> entity = typesRepository.findAll();
        List<TypesDto> list = new ArrayList<>();
        for (TypesEntity typesEntity : entity) {
            list.add(toTypesDto(typesEntity));
        }
        return list;
    }

    public List<TypesDto> langTypesService(LanguageEnum lang) {
        List<TypesEntity> entity = typesRepository.findAllByOrderByCreatedDate();
        List<TypesDto> list = new ArrayList<>();

        for (TypesEntity typesEntity : entity) {
            TypesDto typesDto = new TypesDto();
            typesDto.setId(typesEntity.getId());
            typesDto.setKey(typesEntity.getKey());

            switch (lang) {
                case UZ -> typesDto.setNameUz(typesEntity.getNameUz());
                case RU -> typesDto.setNameRu(typesEntity.getNameRu());
                case EN -> typesDto.setNameEn(typesEntity.getNameEn());
            }

            typesDto.setVisible(typesEntity.getVisible());
            typesDto.setCreatedDate(typesEntity.getCreatedDate());
            list.add(typesDto);
        }
        return list;
    }

    public List<TypesDto> findAllLang2(LanguageEnum lang) {
        List<TypesMapper> typesMappers = typesRepository.findAllLang(lang.name());
        List<TypesDto> list = new ArrayList<>();
        for (TypesMapper entity : typesMappers) {
            TypesDto typesDto = new TypesDto();
            typesDto.setId(entity.getId());
            typesDto.setName(entity.getName());
            typesDto.setKey(entity.getKey());
            list.add(typesDto);
        }
        return list;
    }


    // TODO                          METHOD

    public TypesEntity get(Integer id) {
        return typesRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Types not found");
        });
    }


    public TypesDto toTypesDto(TypesEntity typesEntity) {
        TypesDto typesDto = new TypesDto();
        typesDto.setId(typesEntity.getId());
        typesDto.setKey(typesEntity.getKey());
        typesDto.setNameUz(typesEntity.getNameUz());
        typesDto.setNameRu(typesEntity.getNameRu());
        typesDto.setNameEn(typesEntity.getNameEn());
        typesDto.setVisible(typesEntity.getVisible());
        typesDto.setCreatedDate(typesEntity.getCreatedDate());

        return typesDto;
    }


}
