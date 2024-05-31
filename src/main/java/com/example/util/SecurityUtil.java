package com.example.util;

import com.example.dto.article.ArticleJwtDto;
import com.example.dto.auth.JwtDTO;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;
import com.example.exp.AppForbiddenException;

public class SecurityUtil {
    public static JwtDTO getJwtDTO(String token) {
        String jwt = token.substring(7); // Bearer eyJhb
        JwtDTO dto = JWTUtil.decode(jwt);
        return dto;
    }

    public static JwtDTO getJwtDTO(String token, ProfileRole requiredRole) {
        JwtDTO dto = getJwtDTO(token);
        if (!dto.getRole().equals(requiredRole)) {
            throw new AppForbiddenException("Method not allowed");
        }
        return dto;
    }

    // todo Article
    public static ArticleJwtDto getArticleJwtDto(String token) {
        String jwt = token.substring(7);
        ArticleJwtDto dto = JWTUtil.decodeArticle(jwt);
        return dto;
    }

    public static ArticleJwtDto getCheckArticle(String token, ArticleStatus status) {
        ArticleJwtDto dto = getArticleJwtDto(token);
        if (!dto.getStatus().equals(status)) {
            throw new AppForbiddenException("Method not allowed");
        }
        return dto;
    }

}
