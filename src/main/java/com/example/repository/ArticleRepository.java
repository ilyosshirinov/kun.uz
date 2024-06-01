package com.example.repository;

import com.example.entity.ArticleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, Integer> {

    List<ArticleEntity> findByImageId(Integer imageId);

    @Query("from ArticleEntity as s where  s.id = ?1")
    ArticleEntity getByIdArticle(String id);

    @Query(value = "select count(*) from region r, category c where r.id=?1 and c.id=?2", nativeQuery = true)
    Double getAllRegionAndCategory(Integer regionId, Integer categoryId);
}
