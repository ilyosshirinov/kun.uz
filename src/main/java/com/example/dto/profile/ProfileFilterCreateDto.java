package com.example.dto.profile;

import com.example.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileFilterCreateDto {
    private String name;
    private String username;
    private String phone;
    private ProfileRole role;
    private LocalDate createDateFrom;
    private LocalDate createDateTo;

}
