package com.example.repository;

import com.example.entity.SmsHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer> {

}
