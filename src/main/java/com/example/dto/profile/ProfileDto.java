package com.example.dto.profile;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {

    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String phone;

    private String password;

    private ProfileStatus status;

    private ProfileRole role;

    private Boolean visible;


    private LocalDate createdDate;

    private String photoId;
}
