package com.example.service;

import com.example.dto.ProfileCreateDto;
import com.example.dto.ProfileDto;
import com.example.entity.ProfileEntity;

import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDto createProfileService(ProfileCreateDto profileCreateDto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(profileCreateDto.getName());
        entity.setSurname(profileCreateDto.getSurname());
        entity.setEmail(profileCreateDto.getEmail());
        entity.setPhone(profileCreateDto.getPhone());
        entity.setPassword(profileCreateDto.getPassword());
        entity.setStatus(profileCreateDto.getStatus());
        entity.setRole(profileCreateDto.getRole());
        entity.setPhotoId(profileCreateDto.getPhotoId());

        profileRepository.save(entity);

        return toProfileDto(entity);
    }

    public Boolean updateAdminProfileService(Integer id, Role role, ProfileCreateDto profileCreateDto) {
        ProfileEntity entity = get(id);
        if (role.equals(Role.ADMIN)) {
            entity.setName(profileCreateDto.getName());
            entity.setSurname(profileCreateDto.getSurname());
            entity.setEmail(profileCreateDto.getEmail());
            entity.setPhone(profileCreateDto.getPhone());
            entity.setPassword(profileCreateDto.getPassword());
            entity.setStatus(profileCreateDto.getStatus());
            entity.setRole(profileCreateDto.getRole());
            entity.setPhotoId(profileCreateDto.getPhotoId());

            profileRepository.save(entity);

            return true;
        } else {
            return false;
        }
    }

    public Boolean updateAnyProfileService(String password, ProfileCreateDto profileCreateDto) {
        ProfileEntity entity = profileRepository.getByPassword(password);
        if (entity != null) {
            entity.setName(profileCreateDto.getName());
            entity.setSurname(profileCreateDto.getSurname());
            entity.setEmail(profileCreateDto.getEmail());
            entity.setPhone(profileCreateDto.getPhone());
            entity.setPassword(profileCreateDto.getPassword());
            entity.setStatus(profileCreateDto.getStatus());
            entity.setRole(profileCreateDto.getRole());
            entity.setPhotoId(profileCreateDto.getPhotoId());

            profileRepository.save(entity);
            return true;
        }
        return false;


    }

    public Page<ProfileDto> allAdminProfileService(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProfileEntity> entityPage = profileRepository.findAll(pageable);
        return toPageDto(entityPage, pageable);
    }


    public Boolean deleteByIdAdminProfile(Integer id) {
        get(id);
        ProfileEntity entity = profileRepository.byIdDelete(id);
        if (entity != null) {
            profileRepository.delete(entity);
            return true;
        }
        return false;
    }


    //  TODO METHOD

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Types not found");
        });
    }

    public ProfileDto toProfileDto(ProfileEntity entity) {
        ProfileDto dto = new ProfileDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPassword(entity.getPassword());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhotoId(entity.getPhotoId());

        return dto;
    }


    public Page<ProfileDto> toPageDto(Page<ProfileEntity> entityPage, Pageable pageable) {
        List<ProfileDto> list = new ArrayList<>();
        for (ProfileEntity entity : entityPage) {
            ProfileDto dto = new ProfileDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dto.setEmail(entity.getEmail());
            dto.setPhone(entity.getPhone());
            dto.setPassword(entity.getPassword());
            dto.setStatus(entity.getStatus());
            dto.setRole(entity.getRole());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setPhotoId(entity.getPhotoId());
            list.add(dto);
        }
        Long totalElements = entityPage.getTotalElements();
        return new PageImpl<>(list, pageable, totalElements);
    }

}
