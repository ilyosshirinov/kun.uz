package com.example.service;

import com.example.dto.auth.AuthDto;
import com.example.dto.auth.AuthRegistrationDto;
import com.example.dto.profile.ProfileDto;
import com.example.entity.ProfileEntity;
import com.example.entity.SmsHistoryEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.repository.SmsHistoryRepository;
import com.example.util.JWTUtil;
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
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;
    @Autowired
    private SmsHistoryService smsService;

    // todo Registration with email
    public String registrationEmailService(AuthRegistrationDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Email allaqachon mavjud");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));

        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);
        sendToRegistrationEmail(entity.getId(), dto.getEmail());

        return "Roʻyxatdan oʻtishni yakunlash uchun elektron pochtangizni tasdiqlang.";
    }

    // todo Registration with phone
    public String registrationPhoneService(AuthRegistrationDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isEmpty()) {
            throw new AppBadException(dto.getPhone() + " allaqachon mavjud");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));

        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);
        smsService.sendSms(dto.getPhone());
        return "Roʻyxatdan oʻtishni yakunlash uchun telefon raqamingizni tasdiqlang!";
    }


    // todo Authorization with email
    public String authorizationWithEmailService(Integer userId) {// todo Emailga yuboramiz 1 kungacha
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();

        emailHistoryService.isNotExpiredEmail(entity.getEmail());// check for expireation date

        if (!entity.getVisible() && !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration tugallanmagan");
        }

        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "Success";
    }

    //    todo Authorization with phone
    public String authorizationWithPhoneService(String code, String phone) {
        Optional<ProfileEntity> entity = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (entity.isEmpty()) {
            throw new AppBadException("Profile not found");
        }
        ProfileEntity profile = entity.get();
        if (!profile.getVisible() && !profile.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration is not completed");
        }
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findByPhone(phone);
        if (optional.isEmpty()) {
            throw new AppBadException(phone + " no'mer topilmadi.");
        }

        SmsHistoryEntity smsHistory = optional.get();
        if (!smsHistory.getCode().equals(code)) {
            throw new AppBadException("Bu kod yaroqsiz.");
        }
        smsService.isNotExpiredSms(phone);
        profileRepository.updateStatus(profile.getId(), ProfileStatus.ACTIVE);
        return "Telefon raqamingiz bilan roʻyxatdan oʻtganligingiz tasdiqlandi!";
    }

    // todo Authorization link or code resend to email
    public String authorizeResendEmailService(String email) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email not exists");
        }
        ProfileEntity entity = optional.get();

        if (!entity.getVisible() && !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Roʻyxatdan oʻtish tugallanmagan");
        }

        emailHistoryService.checkEmailLimit(email);
        sendToRegistrationEmail(entity.getId(), email);
        return "Roʻyxatdan oʻtishni yakunlash uchun elektron pochtangizni tasdiqlang.";
    }

    // todo Authorization code resend to phone
    public String authorizeResendPhoneService(String phone) {
        Optional<ProfileEntity> profileEntity = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (profileEntity.isEmpty()) {
            throw new AppBadException("Profile not found");
        }
        ProfileEntity profile = profileEntity.get();
        if (!profile.getVisible() && profile.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Roʻyxatdan oʻtish tugallanmagan");
        }
        smsService.checkSmsLimit(phone);
        smsService.sendSms(phone);
        return "Roʻyxatdan oʻtishni yakunlash uchun telefoningizni tasdiqlang!";
    }

    // todo Login with email
    public ProfileDto loginWithEmailService(AuthDto authDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(authDTO.getEmail());
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getPassword().equals(MD5Util.getMD5(authDTO.getPassword()))) {
            throw new AppBadException("Wrong password");
        }
        if (entity.getStatus() != ProfileStatus.ACTIVE) {
            throw new AppBadException("User is not active");
        }
        ProfileDto dto = new ProfileDto();
//        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
//        dto.setStatus(entity.getStatus());
        dto.setJwt(JWTUtil.encode(entity.getId(), entity.getRole()));
        return dto;
    }


    // todo Login with phone
    public ProfileDto loginWithPhoneService(String phone, String password) {
        Optional<ProfileEntity> profileEntity = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (profileEntity.isEmpty()) {
            throw new AppBadException(phone + " mavjud emas");
        }
        ProfileEntity entity = profileEntity.get();
        if (!entity.getPassword().equals(MD5Util.getMD5(password))) {
            throw new AppBadException("Noto'g'ri parol");
        }

        if (entity.getStatus() != ProfileStatus.ACTIVE) {
            throw new AppBadException("Foydalanuvchi ACTIVE emas");
        }
        ProfileDto dto = new ProfileDto();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        return dto;
    }


    // TODO          METHOD
    // todo Send link or code for registration
    public void sendToRegistrationEmail(Integer profileId, String email) {
        // send email
        String url = "http://localhost:8020/api/verification/email/" + profileId;
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
        emailHistoryService.crete(email, text); // todo Email create history
    }


}

