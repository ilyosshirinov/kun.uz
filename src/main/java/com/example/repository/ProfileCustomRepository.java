package com.example.repository;

import com.example.dto.profile.ProfileFilterCreateDto;
import com.example.dto.profile.ProfileResponseDto;
import com.example.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public ProfileResponseDto<ProfileEntity> filterProfile(ProfileFilterCreateDto filterDto, Integer page, Integer size) {
        Map<String, Object> param = new HashMap<>();
        StringBuilder query = new StringBuilder();

        if (filterDto.getName() != null) {
            query.append(" and s.name =: name");
            param.put("name", filterDto.getName());
        }

        if (filterDto.getUsername() != null) {
            query.append(" and s.username =: username");
            param.put("username", filterDto.getUsername());
        }
        if (filterDto.getPhone() != null) {
            query.append(" and s.phone =: phone");
            param.put("phone", filterDto.getPhone());
        }
        if (filterDto.getRole() != null) {
            query.append(" and s.role =: role");
            param.put("role", filterDto.getRole());
        }
        if (filterDto.getCreateDateFrom() != null) {
            query.append(" and s.createdDate >= :createDateFrom");
            param.put("createDateFrom", filterDto.getCreateDateFrom());
        }
        if (filterDto.getCreateDateTo() != null) {
            query.append(" and s.createdDate <= :createDateTo");
            param.put("createDateTo", filterDto.getCreateDateTo());
        }

        // todo from ProfileEntity s where s.visible = true and s.create_date between createDateFrom and createDateTo

        StringBuilder selectSql = new StringBuilder(" from ProfileEntity s where s.visible = true");
        StringBuilder countSql = new StringBuilder(" select count(s) from ProfileEntity s where s.visible = true");
        selectSql.append(query);
        countSql.append(query);

        Query selectQuery = entityManager.createQuery(selectSql.toString());
        Query countQuery = entityManager.createQuery(countSql.toString());

        for (Map.Entry<String, Object> entry : param.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        selectQuery.setFirstResult(page * size);   // todo offset Birinchi natijani o'rnatish
        selectQuery.setMaxResults(size);           // todo limit  Maksimal natijalarni o'rnating

        List<ProfileEntity> profileEntityList = selectQuery.getResultList();

        // todo count
        Long totalCount = (Long) countQuery.getSingleResult();

        return new ProfileResponseDto<>(profileEntityList, totalCount);
    }
}
