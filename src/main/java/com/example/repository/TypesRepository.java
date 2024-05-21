package com.example.repository;

import com.example.Mapper.TypesMapper;
import com.example.entity.TypesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypesRepository extends CrudRepository<TypesEntity, Integer>, PagingAndSortingRepository<TypesEntity, Integer> {
    List<TypesEntity> findAllByOrderByCreatedDate();


    @Query(value = """
            select id,
            case :lang
            when 'UZ' Then name_uz
            when 'RU' Then name_ru
            when 'EN' Then name_en
            end as name,
            key
            from types order by created_date
            """, nativeQuery = true)
    List<TypesMapper> findAllLang(@Param("lang") String lang);


}
