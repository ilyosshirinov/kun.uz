package com.example.service;

import com.example.dto.auth.AuthRegistrationDto;
import com.example.entity.ProfileEntity;
import com.example.enums.Role;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MailSenderService mailSenderService;

    public String registration(AuthRegistrationDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Email already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));

        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(Role.ROLE_USER);
        entity.setStatus(ProfileStatus.ACTIVE);

        profileRepository.save(entity);

        // send email
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Click to the link to complete registration \n");
        stringBuilder.append("http://localhost:8080/auth/verification/");
        stringBuilder.append(entity.getId());
        stringBuilder.append("\n Mazgi.");

        mailSenderService.send(dto.getEmail(), "Complete registration", stringBuilder.toString());
        return "To complete your registration please verify your email.";

    }

    public String authorizationVerification(Integer userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }
        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "Success";
    }

}
