package com.example.dto.article;

import com.example.dto.category.CategoryDto;
import com.example.dto.profile.ProfileDto;
import com.example.dto.region.RegionDto;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDto {
    private String id;

    private String title;   // Yangilikning nomi

    private String description; /// Yangilik haqida qisqacha malumot

    private String content;       ///Malumotni to'liq qismi

    private Integer sharedCount;  // Yangilikni ulashilganlar soni

    private Integer viewCount;    // Yangilikni ko'rilganlar soni

    private Integer imageId;      // Yangilikni rasmining Id si

    private LocalDateTime createDate;  // Yangilikni yozilgan vaqti

    private LocalDateTime published_date;                 // Yangilik tahrir(tekshiruv)dan o'tgan va hammaga ko'rsatilgan vaqti

    private RegionDto region;                   // BU yangilik qayer(region) da sodir bo'ldi

    private CategoryDto category;              // BU yangilik qanday category ga tegishli  bo'ladi

    private ProfileDto moderator;                  // Yangilikni yozgan odam

    private ProfileDto publisher;                  // Yangilikni tahrir(tekshirgan) odam

    private ProfileRole status;

    private Boolean visible;


}
