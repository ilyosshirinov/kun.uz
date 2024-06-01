package com.example.controller;


import com.example.dto.types.TypesCreateDto;
import com.example.dto.types.TypesDto;
import com.example.enums.LanguageEnum;
import com.example.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypesController {

    @Autowired
    private TypesService typesService;

    @PostMapping("/adm/create")
    public ResponseEntity<TypesDto> createTypes(/*@RequestHeader("Authorization") String token,*/
                                                @RequestBody TypesCreateDto typesCreateDto) {
        // todo 1. Create  (ADMIN) (order_number,name_uz, name_ru, name_en)
        /*SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(typesService.createTypesService(typesCreateDto));
    }

    @PostMapping("/adm/updateById")
    public ResponseEntity<TypesDto> updateByIdTypes(/*@RequestHeader("Authorization") String token,*/
                                                    @RequestBody TypesCreateDto typesCreateDto) {
        // todo  2. Update by id (ADMIN) (order_number,name_uz, name_ru, name_en)
        /*SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(typesService.updateByIdTypesService(typesCreateDto));
    }

    @PostMapping("/adm/deleteById")
    public ResponseEntity<Boolean> deleteByIdTypes(/*@RequestHeader("Authorization") String token,*/
                                                   @RequestBody Integer id) {
        // todo 3. Delete by id (ADMIN)
        /*SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(typesService.deleteByIdTypesService(id));
    }

    @GetMapping("/adm/all")
    public ResponseEntity<Page<TypesDto>> allTypes(/*@RequestHeader("Authorization") String token,*/
                                                   @RequestParam("page") Integer page,
                                                   @RequestParam("size") Integer size) {
        // todo 4. Get List (ADMIN) (Pagination) (id, key, name_uz, name_ru, name_en, visible, created_date) // xato
        /*SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(typesService.allTypesService(page, size));
    }

    @GetMapping("/any/lang")
    public ResponseEntity<List<TypesDto>> langTypes(@RequestHeader(value = "lang", defaultValue = "UZ") LanguageEnum lang) {
        // todo 5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
        //        (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.)
        return ResponseEntity.ok(typesService.langTypesService(lang));
    }

    @GetMapping("/any/lang2")
    public ResponseEntity<List<TypesDto>> langTypes2(@RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                     LanguageEnum lang) {
        // todo 5.2 Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
        //        (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.)
        return ResponseEntity.ok(typesService.findAllLang2(lang));
    }

}
