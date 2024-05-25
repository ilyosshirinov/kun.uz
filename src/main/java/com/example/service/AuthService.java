package com.example.service;

import com.example.dto.auth.AuthRegistrationDto;
import com.example.dto.profile.ProfileDto;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
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
    @Autowired
    private EmailHistoryService emailHistoryService;

    public String registrationService(AuthRegistrationDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Email allaqachon mavjud");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));

        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);
        sendRegistrationEmail(entity.getId(), dto.getEmail());

        return "Roʻyxatdan oʻtishni yakunlash uchun elektron pochtangizni tasdiqlang.";
    }

    public String authorizationVerificationService(Integer userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();

        emailHistoryService.isNotExpiredEmail(entity.getEmail());// check for expireation date

        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration tugallanmagan");
        }

        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "Success";
    }

    public String registrationResendService(String email) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email not exists");
        }
        ProfileEntity entity = optional.get();

        if (!entity.getVisible() && !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Roʻyxatdan oʻtish tugallanmagan");
        }

        emailHistoryService.checkEmailLimit(email);
        sendRegistrationEmail(entity.getId(), email);
        return "Roʻyxatdan oʻtishni yakunlash uchun elektron pochtangizni tasdiqlang.";
    }

    public ProfileDto loginAuthService(String email, String password) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email not exists");
        }

        ProfileEntity entity = optional.get();
        if (!entity.getPassword().equals(MD5Util.getMD5(password))) {
            throw new AppBadException("Noto'g'ri parol");
        }

        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppBadException("User ro'yhatdan o'tmagan");
        }
        ProfileDto dto = new ProfileDto();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        return dto;
    }


    // TODO          METHOD
    public void sendRegistrationEmail(Integer profileId, String email) {
        // send email
        String url = "http://localhost:8020/api/verification/" + profileId;
        String formatText = "<style>\n" +
                "    a:link, a:visited {\n" +
                "        background-color: #f44336;\n" +
                "        color: white;\n" +
                "        padding: 14px 25px;\n" +
                "        text-align: center;\n" +
                "        text-decoration: none;\n" +
                "        display: inline-block;\n" +
                "    }\n" +
                "\n" +
                "    a:hover, a:active {\n" +
                "        background-color: red;\n" +
                "    }\n" +
                "</style>\n" +
                "<div style=\"text-align: center\">\n" +
                "    <h1>Welcome to kun.uz web portal</h1>\n" +
                "    <br>\n" +
                "    <p>Please button lick below to complete registration</p>\n" +
                "    <div style=\"text-align: center\">\n" +
                "        <a href=\"%s\" target=\"_blank\">This is a link</a>\n" +
                "    </div>";
        String text = String.format(formatText, url);
        mailSenderService.send(email, "Complete registration", text);
        emailHistoryService.crete(email, text); // create history
    }


}

