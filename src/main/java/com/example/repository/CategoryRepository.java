package com.example.repository;

import com.example.Mapper.CategoryMapper;
import com.example.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAllByOrderByOrderNumberAsc();

    List<CategoryEntity> findAllByVisibleTrueOrderByOrderNumberDesc();

    @Query(value = """
            select s.id,
            case :lang
            when 'UZ' Then name_uz
            when 'RU' Then name_ru
            when 'EN' Then name_en
            end as name,
            s.order_number
            from category as s;
            """, nativeQuery = true)
    List<CategoryEntity> getByLang(@Param("lang") String lang);

}
