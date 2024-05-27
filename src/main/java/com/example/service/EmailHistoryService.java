package com.example.service;

import com.example.dto.emailHistory.EmailHistoryDto;
import com.example.entity.EmailHistoryEntity;
import com.example.exp.AppBadException;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public List<EmailHistoryDto> getAllEmailService(String email) {
        List<EmailHistoryEntity> entity = emailHistoryRepository.getAllByEmail(email);
        if (entity.size() == 0) {
            throw new AppBadException(email + " mavjud emas!");
        }
        List<EmailHistoryDto> list = new ArrayList<>();
        for (EmailHistoryEntity emailHistoryEntity : entity) {
            EmailHistoryDto dto = new EmailHistoryDto();
            dto.setId(emailHistoryEntity.getId());
            dto.setEmail(emailHistoryEntity.getEmail());
            dto.setMessage(emailHistoryEntity.getMessage());
            dto.setCreatedDate(emailHistoryEntity.getCreatedDate());
            list.add(dto);
        }
        return list;
    }

    public List<EmailHistoryDto> getByCreatedDateEmailService(LocalDate date) {
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN); // 2024-05-25 00:00:00
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX); // 2024-05-25 23:59:59.99999999
        List<EmailHistoryEntity> entity = emailHistoryRepository.getAllCreatedDate(from, to);
        if (entity.size() == 0) {
            throw new AppBadException(date + " kunda Email mavjud emas!");
        }
        List<EmailHistoryDto> list = new ArrayList<>();
        for (EmailHistoryEntity emailHistoryEntity : entity) {
            EmailHistoryDto dto = new EmailHistoryDto();
            dto.setId(emailHistoryEntity.getId());
            dto.setEmail(emailHistoryEntity.getEmail());
            dto.setMessage(emailHistoryEntity.getMessage());
            dto.setCreatedDate(emailHistoryEntity.getCreatedDate());
            list.add(dto);
        }
        return list;
    }

    public Page<EmailHistoryDto> getAllPageEmailService(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailHistoryEntity> entityPage = emailHistoryRepository.findAll(pageable);
        return toDtoEmail(entityPage, pageable); // todo kurish kerak
    }


    // TODO       METHOD
    public void crete(String toEmail, String text) { // todo Shu joyda Email tablga create qilayapmiz
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(toEmail);
        entity.setMessage(text);
        emailHistoryRepository.save(entity);
    }

    public void checkEmailLimit(String email) { // todo 1 minute -3 attempt
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(2);

        long count = emailHistoryRepository.countByEmailAndCreatedDateBetween(email, from, to);
        if (count >= 3) {
            throw new AppBadException("SMS limitiga yetdi. Iltimos, biroz vaqtdan keyin harakat qilib ko'ring");
        }
    }

    public void isNotExpiredEmail(String email) {
        Optional<EmailHistoryEntity> optional = emailHistoryRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email topilmadi");
        }
        EmailHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Tasdiqlash muddati tugadi");
        }
    }

    public Page<EmailHistoryDto> toDtoEmail(Page<EmailHistoryEntity> entityPage, Pageable pageable) {
        List<EmailHistoryDto> list = new ArrayList<>();
        for (EmailHistoryEntity emailHistoryEntity : entityPage) {
            EmailHistoryDto dto = new EmailHistoryDto();
            dto.setId(emailHistoryEntity.getId());
            dto.setEmail(emailHistoryEntity.getEmail());
            dto.setCreatedDate(emailHistoryEntity.getCreatedDate());
            dto.setMessage(emailHistoryEntity.getMessage());
            list.add(dto);
        }
        Long totalElements = entityPage.getTotalElements();
        return new PageImpl<>(list, pageable, totalElements);
    }
}