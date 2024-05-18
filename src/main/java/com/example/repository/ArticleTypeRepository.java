package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {

}
