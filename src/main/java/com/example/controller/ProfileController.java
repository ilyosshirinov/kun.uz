package com.example.controller;

import com.example.dto.auth.JwtDTO;
import com.example.dto.profile.ProfileCreateDto;

import com.example.dto.profile.ProfileDto;
import com.example.dto.profile.ProfileFilterCreateDto;
import com.example.dto.profile.ProfileUpdateDto;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/create/profile")
    public ResponseEntity<ProfileDto> createProfile(@RequestHeader("Authorization") String token,
                                                    @Valid @RequestBody ProfileCreateDto profileCreateDto) {
        // todo  1. Create profile (ADMIN) (can create MODERATOR,PUBLISHER))
        //       (name,surname,email,phone,password,status,role)
        SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(profileService.createProfileService(profileCreateDto));
    }

    @PostMapping("/updateAdmin/profile")
    public ResponseEntity<Boolean> updateAdminProfile(@RequestParam("id") Integer id,
                                                      @RequestParam("role") ProfileRole role,
                                                      @RequestBody ProfileCreateDto profileCreateDto) {
        // todo 2. Update Profile (ADMIN)
        return ResponseEntity.ok(profileService.updateAdminProfileService(id, role, profileCreateDto));
    }

    @PostMapping("/updateAny/profile") // toda current
    public ResponseEntity<Boolean> updateAnyProfile(@RequestHeader("Authorization") String token,
                                                    @Valid @RequestBody ProfileUpdateDto profileUpdateDto) {
        // todo 3. Update Profile Detail (ANY) (Profile updates own details)
        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        return ResponseEntity.ok(profileService.updateAnyProfileService(dto.getId(), profileUpdateDto));
    }

    @PostMapping("/allAdmin/profile")
    public ResponseEntity<Page<ProfileDto>> allAdminProfile(@RequestParam("page") Integer page,
                                                            @RequestParam("size") Integer size) {
        // todo 4. Profile List (ADMIN) (Pagination)
        return ResponseEntity.ok(profileService.allAdminProfileService(page, size));
    }

    @DeleteMapping("/deleteByIdAdmin/profile")
    public ResponseEntity<Boolean> deleteByIdAdminProfile(@RequestParam("id") Integer id) {
        // todo 5. Delete Profile By Id (ADMIN)
        return ResponseEntity.ok(profileService.deleteByIdAdminProfile(id));
    }

    @PostMapping("/filter/profile")
    public ResponseEntity<Page<ProfileDto>> filterProfile(@RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size,
                                                          @RequestBody ProfileFilterCreateDto filterCreateDto) {
        PageImpl<ProfileDto> profileDto = profileService.filterProfileService(filterCreateDto, page - 1, size);
        return ResponseEntity.ok().body(profileDto);
    }

}
