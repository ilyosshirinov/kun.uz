package com.example.controller;

import com.example.dto.region.RegionCreateDto;
import com.example.dto.region.RegionDto;
import com.example.enums.LanguageEnum;
import com.example.service.RegionService;

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

    @PostMapping("/adm/create")
    public ResponseEntity<RegionDto> createRegion(@Valid @RequestBody RegionCreateDto regionCreateDto) {
        return ResponseEntity.ok(regionService.createRegionService(regionCreateDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RegionDto>> allRegion() {
        return ResponseEntity.ok(regionService.allRegionService());
    }

    @GetMapping("/adm/lang")
    public ResponseEntity<List<RegionDto>> getAllByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        List<RegionDto> regionDTOList = regionService.getAllByLang(lang);
        return ResponseEntity.ok().body(regionDTOList);
    }

    @GetMapping("/lang2")
    public ResponseEntity<List<RegionDto>> getAllByLang2(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
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
