package com.example.repository;

import com.example.entity.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findTopByTokenOrderByExpireDateDesc(String token);
}
