package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer> , PagingAndSortingRepository<EmailHistoryEntity, Integer> {

    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);
    //todo select count(*) from email_history createdDate between :from and

    @Query(" from EmailHistoryEntity  order by createdDate desc limit 1")
    Optional<EmailHistoryEntity> findByEmail(String email);

    List<EmailHistoryEntity> getAllByEmail(String email);

    @Query(value = " select * from email_history where created_date between :from and :to order by created_date", nativeQuery = true)
    List<EmailHistoryEntity> getAllCreatedDate(LocalDateTime from, LocalDateTime to);
//    @Query(value = " select * from email_history where created_date between :from and :to order by created_date", nativeQuery = true)
//    List<EmailHistoryEntity> findAll2(LocalDate from, LocalDate to);


//    @Query("from EmailHistoryEntity as s where s.createdDate=:date")
//    List<EmailHistoryEntity> getAllCreatedDate(LocalDate date);

}
