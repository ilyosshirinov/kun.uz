package com.example.repository;

import com.example.entity.ProfileEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>, PagingAndSortingRepository<ProfileEntity, Integer> {

    ProfileEntity getByPassword(String password);

    @Query("select s from ProfileEntity as s where s.id =:id and s.role = 'ADMIN'")
    ProfileEntity byIdDelete(@Param("id") Integer id);

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);
}
