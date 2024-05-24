package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer> {

    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);
    // select count(*) from email_history createdDate between :from and

    Optional<EmailHistoryEntity> findByEmail(String email);

}
