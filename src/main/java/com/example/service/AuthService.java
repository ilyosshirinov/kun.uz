package com.example.service;

import com.example.dto.auth.AuthRegistrationDto;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    public String authorization(AuthRegistrationDto dto) {
            Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
            if (optional.isPresent()) {
                throw new AppBadException("Email already exists");
            }

            ProfileEntity entity = new ProfileEntity();
            entity.setName(dto.getName());
            entity.setSurname(dto.getSurname());
            entity.setEmail(dto.getEmail());
            entity.setPassword(MD5Util.getMD5(dto.getPassword()));

            entity.setCreatedDate(LocalDate.now());
            entity.setRole(ProfileRole.ROLE_USER);
            entity.setStatus(ProfileStatus.ACTIVE);

            profileRepository.save(entity);

            // send email

            return "To complete your registration please verify your email.";

    }
}
