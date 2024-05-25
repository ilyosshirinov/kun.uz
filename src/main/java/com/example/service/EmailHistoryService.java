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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;


    public void crete(String toEmail, String text) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(toEmail);
        entity.setMessage(text);
        emailHistoryRepository.save(entity);
    }

    public void checkEmailLimit(String email) { // 1 minute -3 attempt
        // 23/05/2024 19:01:13
        // 23/05/2024 19:01:23
        // 23/05/2024 19:01:33

        // 23/05/2024 19:00:55 -- (current -1)
        // 23/05/2024 19:01:55 -- current

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
            throw new AppBadException("Email history not found");
        }
        EmailHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Confirmation time expired");
        }
    }

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
        LocalDate from = date;
        LocalDate to = from.plusDays(1);
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