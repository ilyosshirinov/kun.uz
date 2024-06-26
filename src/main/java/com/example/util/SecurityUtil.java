package com.example.util;

import com.example.config.CustomUserDetail;
import com.example.dto.article.ArticleJwtDto;
import com.example.dto.auth.JwtDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;
import com.example.exp.AppForbiddenException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    public static Integer getProfileId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        return user.getProfile().getId();
    }

    public static ProfileEntity getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        return user.getProfile();
    }

}
