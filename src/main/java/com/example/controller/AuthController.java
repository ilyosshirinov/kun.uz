package com.example.controller;

import com.example.dto.auth.AuthEmailPasswordDto;
import com.example.dto.auth.AuthPhonePasswordDto;
import com.example.dto.auth.AuthRegistrationDto;
import com.example.dto.profile.ProfileDto;
import com.example.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth Controller", description = "Api list for authorization, registration and other ... ")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration/email") // todo Registration with email
    @Operation(summary = "Registration", description = "Api for profile registration")
    public ResponseEntity<String> registrationEmail(@Valid @RequestBody AuthRegistrationDto dto) {
        // todo 1. Registration Email
        log.trace("for tracing purpose: registration");
        log.debug("for debugging purpose: registration");
        log.info("for informational purpose: registration");
        log.warn("for warning purpose: registration");
        log.error("for logging errors: registration");

        String body = authService.registrationEmailService(dto);
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/registration/phone") // todo Registration with phone
    public ResponseEntity<String> registrationPhone(@Valid @RequestBody AuthRegistrationDto dto) {
        // todo 1. Registration Phone
        String body = authService.registrationPhoneService(dto);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/verification/{userId}") // todo Authorize with email
    public ResponseEntity<String> verificationWithEmail(@PathVariable("userId") Integer userId) {
        // todo 3. Verification mail
        String body = authService.authorizationWithEmailService(userId);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/verification/sms") // todo Authorize with phone
    public ResponseEntity<String> verificationWithPhone(@RequestParam("code") String code,
                                                        @RequestParam("phone") String phone) {
        // todo 4. Resent   (phone -> code)
        String response = authService.authorizationWithPhoneService(code, phone);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/registration/resend/{email}") // todo Resend with email
    public ResponseEntity<String> registrationResend(@PathVariable("email") String email) {
        // todo 4. Resent  (mail -> link)
        String body = authService.authorizeResendEmailService(email);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/verification/resend/phone") // todo Resend with phone
    public ResponseEntity<String> verificationResendPhone(@RequestParam("phone") String phone) {
        return ResponseEntity.ok(authService.authorizeResendPhoneService(phone));
    }

    @PostMapping("/login") // todo Login with email
    public ResponseEntity<?> loginAuth(@RequestBody AuthEmailPasswordDto authDto) {
        // todo 2. Login (email/password)
        return ResponseEntity.ok(authService.loginWithEmailService(authDto));
    }

    @PostMapping("/login/phone") // todo Login with phone
    public ResponseEntity<ProfileDto> loginWithPhone(@RequestBody AuthPhonePasswordDto dto) {
        // todo 2. Login (phone/password)
        return ResponseEntity.ok(authService.loginWithPhoneService(dto));
    }
}
