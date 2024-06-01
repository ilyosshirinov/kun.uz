package com.example.controller;

import com.example.dto.article.ArticleCreateDto;
import com.example.dto.article.ArticleDto;
import com.example.entity.ArticleEntity;
import com.example.entity.CategoryEntity;
import com.example.enums.ArticleStatus;
import com.example.service.ArticleService;
import com.example.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<ArticleDto> createArticle(//@RequestHeader("Authorization") String token,
                                                    @RequestBody ArticleCreateDto createDto) {
//        SecurityUtil.getCheckArticle(token, ArticleStatus.NOT_PUBLISHED);
        return ResponseEntity.ok(articleService.createArticleService(createDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ArticleDto>> getAll() {
        return ResponseEntity.ok(articleService.getAll());
    }

    @PostMapping("/update")
    public ResponseEntity<ArticleDto> updateArticle(@RequestHeader("Authorization") String token,
                                                    @RequestParam("imageId") Integer imageId,
                                                    @RequestBody ArticleCreateDto createDto) {
        SecurityUtil.getCheckArticle(token, ArticleStatus.NOT_PUBLISHED);
        return ResponseEntity.ok(articleService.updateArticleService(imageId, createDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteArticle(@RequestHeader("Authorization") String token,
                                                @RequestParam("id") String id) {
        SecurityUtil.getCheckArticle(token, ArticleStatus.NOT_PUBLISHED);
        return ResponseEntity.ok(articleService.deleteArticleService(id));
    }

}
