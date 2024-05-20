package com.example.controller;

import com.example.dto.CategoryCreateDto;
import com.example.dto.CategoryDto;
import com.example.dto.TypesCreateDto;
import com.example.dto.TypesDto;
import com.example.enums.LanguageEnum;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create/category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryCreateDto categoryCreateDto) {
        // todo 1. Create  (ADMIN)  (order_number,name_uz, name_ru, name_en)
        return ResponseEntity.ok(categoryService.createCategoryService(categoryCreateDto));
    }

    @PostMapping("/updateById/category")
    public ResponseEntity<CategoryDto> updateByIdCategory(@RequestParam("id") Integer id,
                                                          @RequestBody CategoryCreateDto categoryCreateDto) {
        // todo  2. Update by id (ADMIN)  (order_number,name_uz, name_ru, name_en)
        return ResponseEntity.ok(categoryService.updateByIdCategoryService(id, categoryCreateDto));
    }

    @DeleteMapping("/deleteById/category")
    public ResponseEntity<Boolean> deleteByIdCategory(@RequestParam("id") Integer id) {
        // todo 3. Delete by id (ADMIN)
        return ResponseEntity.ok(categoryService.deleteByIdCategoryService(id));
    }

    @GetMapping("/all/category")
    public ResponseEntity<List<CategoryDto>> allCategory() {
        // todo 4. Get List (ADMIN) - order by order_number (id,order_number,name_uz, name_ru, name_en,visible,created_date)
        return ResponseEntity.ok(categoryService.allCategoryService());
    }

    @GetMapping("/lang/category")
    public ResponseEntity<List<CategoryDto>> langCategory(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        return ResponseEntity.ok(categoryService.langCategoryService(lang));
    }

    @GetMapping("/lang2/category")
    public ResponseEntity<List<CategoryDto>> langCategory2(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        return ResponseEntity.ok(categoryService.langCategoryService2(lang));
    }
}
