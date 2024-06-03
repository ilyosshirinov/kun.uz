package com.example.controller;

import com.example.dto.auth.JwtDTO;
import com.example.dto.profile.ProfileCreateDto;
import com.example.dto.profile.ProfileDto;
import com.example.dto.profile.ProfileFilterCreateDto;
import com.example.dto.profile.ProfileUpdateDto;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm/create")
    public ResponseEntity<ProfileDto> createProfile(
            @Valid @RequestBody ProfileCreateDto profileCreateDto
            /*HttpServletRequest request*/) {
        // todo  1. Create profile (ADMIN) (can create MODERATOR,PUBLISHER))
        //       (name,surname,email,phone,password,status,role)
        /*JwtDTO jwtDTO = HttpRequestUtil.getJwtDTO(request, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(profileService.createProfileService(profileCreateDto));
    }

    @PostMapping("/adm/update")
    public ResponseEntity<ProfileDto> updateAdminProfile(/*@RequestHeader("Authorization") String token,*/
            @RequestBody ProfileCreateDto profileCreateDto) {
        // todo 2. Update Profile (ADMIN)
        /*SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(profileService.updateAdminProfileService(profileCreateDto));
    }

    @PostMapping("/updateAny") // todo current
    public ResponseEntity<Boolean> updateAnyProfile(/*@RequestHeader("Authorization") String token,*/
            @RequestParam("id") Integer id,
            @Valid @RequestBody ProfileUpdateDto profileUpdateDto) {
        // todo 3. Update Profile Detail (ANY) (Profile updates own details)
//        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        return ResponseEntity.ok(profileService.updateAnyProfileService(id, profileUpdateDto));
    }

    @PostMapping("/adm/all")
    public ResponseEntity<Page<ProfileDto>> allAdminProfile(/*@RequestHeader("Authorization") String token,*/
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        // todo 4. Profile List (ADMIN) (Pagination)
        /*SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(profileService.allAdminProfileService(page, size));
    }

    @PostMapping("/adm/deleteById")
    public ResponseEntity<Boolean> deleteByIdAdminProfile(/*@RequestHeader("Authorization") String token,*/
            @RequestBody Integer id) {
        // todo 5. Delete Profile By Id (ADMIN)
        /*SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);*/
        return ResponseEntity.ok(profileService.deleteByIdAdminProfile(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<ProfileDto>> filterProfile(@RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size,
                                                          @RequestBody ProfileFilterCreateDto filterCreateDto) {
        PageImpl<ProfileDto> profileDto = profileService.filterProfileService(filterCreateDto, page - 1, size);
        return ResponseEntity.ok().body(profileDto);
    }

}
