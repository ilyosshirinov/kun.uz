package com.example.repository;

import com.example.Mapper.RegionMapper;
import com.example.entity.RegionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {
    List<RegionEntity> findAllByVisibleTrueOrderByOrderNumberDesc();

    @Query("from RegionEntity where visible = true order by orderNumber desc")
    List<RegionEntity> findAllVisible();

    @Query(value = " select id, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN name_uz " +
            "   WHEN 'EN' THEN name_en " +
            "   WHEN 'RU' THEN name_ru " +
            "  END as name " +
            "from region  order by order_number; ", nativeQuery = true)
    List<RegionMapper> findAll(@Param("lang") String lang);


}
