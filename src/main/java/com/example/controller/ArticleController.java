package com.example.controller;

import com.example.dto.article.ArticleCreateDto;
import com.example.dto.article.ArticleDto;
import com.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/moderator")
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleCreateDto createDto) {
        // todo 1. CREATE (Moderator) status(NotPublished)
        //         (title,description,content,image_id, region_id,category_id, articleType(array))
        return ResponseEntity.ok(articleService.createArticleService(createDto));
    }


//
//    @GetMapping("/adm/getAll")
//    public ResponseEntity<List<ArticleDto>> getAll() {
//        return ResponseEntity.ok(articleService.getAll());
//    }
//
//    @PostMapping("/adm/update")
//    public ResponseEntity<Boolean> updateArticle(/*@RequestHeader("Authorization") String token,*/
//            @RequestParam("imageId") Integer imageId,
//            @RequestBody ArticleCreateDto createDto) {
//        /*SecurityUtil.getCheckArticle(token, ArticleStatus.NOT_PUBLISHED);*/
//        // todo 2. Update (Moderator (status to not publish)) (remove old image)
//        //         (title,description,content,shared_count,image_id, region_id,category_id)
//        return ResponseEntity.ok(articleService.updateArticleService(imageId, createDto));
//    }
//
//    @DeleteMapping("/adm/delete")
//    public ResponseEntity<String> deleteArticle(/*@RequestHeader("Authorization") String token,*/
//            @RequestParam("id") String id) {
//        /*SecurityUtil.getCheckArticle(token, ArticleStatus.NOT_PUBLISHED);*/
//        // todo 3. Delete Article (MODERATOR)
//        return ResponseEntity.ok(articleService.deleteArticleService(id));
//    }

}
