package com.example.dto.auth;

import com.example.enums.ProfileRole;
import lombok.Data;

@Data
public class JwtDTO {
    private Integer id;
    private String username;
    private ProfileRole role;

    public JwtDTO(Integer id, String username, ProfileRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public JwtDTO(Integer id) {
        this.id = id;
    }
}
