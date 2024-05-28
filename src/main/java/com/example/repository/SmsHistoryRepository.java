package com.example.repository;

import com.example.entity.SmsHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer> {
    @Query("from SmsHistoryEntity order by createdDate desc limit 1")
    Optional<SmsHistoryEntity> findByPhone(String phone);

    Long countByPhoneAndCreatedDateBetween(String phone, LocalDateTime to, LocalDateTime from);
}
