package com.example.dto;

import com.example.enums.Role;
import com.example.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileCreateDto {
    @NotBlank(message = "name required")
    private String name;

    @NotBlank(message = "surname required")
    private String surname;

    @NotBlank(message = "email required")
    @Email
    private String email;

    @NotBlank(message = "phone required")
    private String phone;

    @NotBlank(message = "password required")
    private String password;

    private String photoId;

    private Status status;

    private Role role;
}
