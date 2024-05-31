package com.example.controller;

import com.example.dto.region.RegionCreateDto;
import com.example.dto.region.RegionDto;
import com.example.enums.LanguageEnum;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;

import com.example.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping("/create/region")
    public ResponseEntity<RegionDto> createRegion(@RequestHeader("Authorization") String token,
                                                  @Valid @RequestBody RegionCreateDto regionCreateDto) {
        // todo 1. Create  (ADMIN) (order_number,name_uz, name_ru, name_en)
        SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.createRegionService(regionCreateDto));
    }

    @PostMapping("/update/region")
    public ResponseEntity<?> updateRegion(@RequestHeader("Authorization") String token,
                                          @RequestParam("id") Integer id,
                                          @Valid @RequestBody RegionCreateDto regionCreateDto) {
        // todo 2. Update by id (ADMIN) (name_uz, name_ru, name_en)
        SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.updateRegionService(id, regionCreateDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteRegion(@RequestHeader("Authorization") String token,
                                                @PathVariable("id") Integer id) {
        // todo 3. Delete by id (ADMIN)
        SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.delete(id));
    }

    @GetMapping("/all/region")
    public ResponseEntity<List<RegionDto>> allRegion(@RequestHeader("Authorization") String token) {
        // todo 4. Get List (ADMIN) (id,name_uz, name_ru, name_en,visible,order_number,created_date)
        SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.allRegionService());
    }

    @GetMapping("/adm/lang")
    public ResponseEntity<List<RegionDto>> getAllByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        // todo 5.1 Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
        //        (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.) (visible true)
        return ResponseEntity.ok(regionService.getAllByLang(lang));
    }

    @GetMapping("/lang2")
    public ResponseEntity<List<RegionDto>> getAllByLang2(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        // todo 5.2 Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
        //        (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.) (visible true)
        List<RegionDto> regionDTOList = regionService.getAllByLang2(lang);
        return ResponseEntity.ok().body(regionDTOList);
    }

    @PostMapping("/adm/update")
    public ResponseEntity<?> updateRegion(@RequestParam("id") Integer id,
                                          @Valid @RequestBody RegionCreateDto regionCreateDto) {
        return ResponseEntity.ok(regionService.updateRegionService(id, regionCreateDto));
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<Boolean> deleteRegion(@PathVariable("id") Integer id) {
        Boolean result = regionService.delete(id);
        return ResponseEntity.ok().body(result);
    }

}
