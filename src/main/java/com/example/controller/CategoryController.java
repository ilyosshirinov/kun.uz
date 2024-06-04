package com.example.controller;

import com.example.dto.category.CategoryCreateDto;
import com.example.dto.category.CategoryDto;
import com.example.enums.LanguageEnum;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm/create")
    public ResponseEntity<CategoryDto> createCategory(/*@RequestHeader("Authorization") String token,*/
                                                      @RequestBody CategoryCreateDto categoryCreateDto) {
        // todo 1. Create  (ADMIN)  (order_number,name_uz, name_ru, name_en)
        /*SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(categoryService.createCategoryService(categoryCreateDto));
    }

    @PostMapping("/adm/updateById")
    public ResponseEntity<CategoryDto> updateByIdCategory(/*@RequestHeader("Authorization") String token,*/
                                                          @RequestParam("id") Integer id,
                                                          @RequestBody CategoryCreateDto categoryCreateDto) {
        // todo  2. Update by id (ADMIN)  (order_number,name_uz, name_ru, name_en)
        /*SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(categoryService.updateByIdCategoryService(id, categoryCreateDto));
    }

    @DeleteMapping("/adm/deleteById")
    public ResponseEntity<Boolean> deleteByIdCategory(/*@RequestHeader("Authorization") String token,*/
                                                      @RequestParam("id") Integer id) {
        // todo 3. Delete by id (ADMIN)
        /*SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(categoryService.deleteByIdCategoryService(id));
    }

    @GetMapping("/adm/all")
    public ResponseEntity<List<CategoryDto>> allCategory(/*@RequestHeader("Authorization") String token*/) {
        // todo 4. Get List (ADMIN) - order by order_number (id,order_number,name_uz, name_ru, name_en,visible,created_date)
        /*SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(categoryService.allCategoryService());
    }

    @GetMapping("/any/lang")
    public ResponseEntity<List<CategoryDto>> langCategory(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        // todo 5.1 Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
        //        (id,order_number,name) (name ga tegishli name_... dagi qiymat qo'yiladi.)
        return ResponseEntity.ok(categoryService.langCategoryService(lang));
    }

    @GetMapping("/any/lang2")
    public ResponseEntity<List<CategoryDto>> langCategory2(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        // todo 5.2 Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
        //        (id,order_number,name) (name ga tegishli name_... dagi qiymat qo'yiladi.)
        return ResponseEntity.ok(categoryService.langCategoryService2(lang));
    }
}
