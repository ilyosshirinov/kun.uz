package com.example.controller;

import com.example.dto.auth.AuthRegistrationDto;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@Valid @RequestBody AuthRegistrationDto dto) {
        String body = authService.authorization(dto);
        return ResponseEntity.ok().body(body);
    }

}
