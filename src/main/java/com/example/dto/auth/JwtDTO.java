package com.example.dto.auth;

import com.example.enums.ProfileRole;
import lombok.Data;

@Data
public class JwtDTO {
    private Integer id;
    private ProfileRole role;

    public JwtDTO(Integer id, ProfileRole role) {
        this.id = id;
        this.role = role;
    }

    public JwtDTO(Integer id) {
        this.id = id;
    }
}
