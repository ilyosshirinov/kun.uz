package com.example.dto.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileUpdateDto {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
}
