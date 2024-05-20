package com.example.controller;


import com.example.dto.TypesCreateDto;
import com.example.dto.TypesDto;
import com.example.enums.LanguageEnum;
import com.example.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TypesController {

    @Autowired
    private TypesService typesService;

    @PostMapping("/create/types")
    public ResponseEntity<TypesDto> createTypes(@RequestBody TypesCreateDto typesCreateDto) {
        // todo 1. Create  (ADMIN) (order_number,name_uz, name_ru, name_en)
        return ResponseEntity.ok(typesService.createTypesService(typesCreateDto));
    }

    @PostMapping("/updateById/types")
    public ResponseEntity<TypesDto> updateByIdTypes(@RequestParam("id") Integer id,
                                                    @RequestBody TypesCreateDto typesCreateDto) {
        // todo  2. Update by id (ADMIN) (order_number,name_uz, name_ru, name_en)
        return ResponseEntity.ok(typesService.updateByIdTypesService(id, typesCreateDto));
    }

    @DeleteMapping("/deleteById/types")
    public ResponseEntity<Boolean> deleteByIdTypes(@RequestParam("id") Integer id) {
        // todo 3. Delete by id (ADMIN)
        return ResponseEntity.ok(typesService.deleteByIdTypesService(id));
    }

    @GetMapping("/all/types")
    public ResponseEntity<List<TypesDto>> allTypes(){
        // todo 4. Get List (ADMIN) (Pagination) (id, key, name_uz, name_ru, name_en, visible, created_date) // xato
        return ResponseEntity.ok(typesService.allTypesService());
    }

    @GetMapping("/lang/types")
    public ResponseEntity<List<TypesDto>> langTypes(@RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                         LanguageEnum lang) {
        // todo 5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
        //        (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.)
        return ResponseEntity.ok(typesService.langTypesService(lang));
    }

    @GetMapping("/lang2/types")
    public ResponseEntity<List<TypesDto>> langTypes2(@RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                    LanguageEnum lang) {
        // todo 5.2 Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
        //        (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.)
        return ResponseEntity.ok(typesService.findAllLang2(lang));
    }

}
