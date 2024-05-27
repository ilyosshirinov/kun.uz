package com.example.controller;

import com.example.dto.auth.AuthRegistrationDto;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody AuthRegistrationDto dto) {
        // todo 1. Registration (only USER) +
        String body = authService.registrationService(dto);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("login/auth")
    public ResponseEntity<?> loginAuth(@RequestParam("email") String email,
                                       @RequestParam("password") String password) {
        // todo 2. Login (email/password)  phone -> tayyormas
        return ResponseEntity.ok(authService.loginAuthService(email, password));
    }

    @GetMapping("/verification/{userId}")
    public ResponseEntity<String> verification(@PathVariable("userId") Integer userId) {
        // todo 3. Verification mail
        String body = authService.authorizationVerificationService(userId);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/registration/resend/{email}")
    public ResponseEntity<String> registrationResend(@PathVariable("email") String email) {
        // todo 4. Resent mail
        String body = authService.registrationResendService(email);
        return ResponseEntity.ok().body(body);
    }


    @PostMapping("/registrationSms")
    public ResponseEntity<String> registrationSms(@Valid @RequestBody AuthRegistrationDto dto) {
        // todo 1. Registration (only USER) +
        String body = authService.registrationSmsService(dto);
        return ResponseEntity.ok().body(body);
    }
}
