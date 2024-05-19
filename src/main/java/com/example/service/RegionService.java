package com.example.service;

import com.example.Mapper.RegionMapper;
import com.example.dto.RegionCreateDto;
import com.example.dto.RegionDto;
import com.example.entity.RegionEntity;
import com.example.enums.LanguageEnum;
import com.example.exp.AppBadException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDto createRegionService(RegionCreateDto dto) {
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        regionRepository.save(entity);

        return toDto(entity);
    }

    public List<RegionDto> allRegionService() {
        Iterable<RegionEntity> regionEntity = regionRepository.findAll();
        List<RegionDto> list = new ArrayList<>();

        for (RegionEntity entity : regionEntity) {
            list.add(toDto(entity));
        }
        return list;
    }


    public List<RegionDto> getAllByLang(LanguageEnum lang) {
        Iterable<RegionEntity> iterable = regionRepository.findAllByVisibleTrueOrderByOrderNumberDesc();
        List<RegionDto> dtoList = new LinkedList<>();
        for (RegionEntity entity : iterable) {
            RegionDto dto = new RegionDto();
            dto.setId(entity.getId());
            switch (lang) {
                case EN -> dto.setName(entity.getNameEn());
                case UZ -> dto.setName(entity.getNameUz());
                case RU -> dto.setName(entity.getNameRu());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<RegionDto> getAllByLang2(LanguageEnum lang) {
        List<RegionMapper> mapperList = regionRepository.findAll(lang.name());
        List<RegionDto> dtoList = new LinkedList<>();
        for (RegionMapper entity : mapperList) {
            RegionDto dto = new RegionDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public Boolean updateRegionService(Integer id, RegionCreateDto dto) {
        RegionEntity entity = get(id);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        regionRepository.save(entity);
        return true;
    }

    public Boolean delete(Integer id) {
        RegionEntity entity = get(id);
        regionRepository.delete(entity);
//        regionRepository.deleteById(id);
        return true;
    }


    // todo Method
    public static RegionDto toDto(RegionEntity entity) {
        RegionDto dto = new RegionDto();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Region not found");
        });
    }


}
