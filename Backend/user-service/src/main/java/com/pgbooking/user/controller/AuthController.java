package com.pgbooking.user.controller;

import com.pgbooking.user.dto.LoginRequest;
import com.pgbooking.user.dto.OtpVerifyRequest;
import com.pgbooking.user.dto.RegisterRequest;
import com.pgbooking.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User APIs",description = "Operation related to User Management")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Register User",
            description = "Register a new user and send OTP"
    )
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request){

        log.info("Received request to /register endpoint");
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(
            summary = "Verify OTP",
            description = "Verify email OTP"
    )
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerifyRequest request){
        log.info("Received request to /verify-otp endpoint");
        return ResponseEntity.ok(authService.verifyOtp(request));
    }

    @Operation(
            summary = "Login User",
            description = "Login and generate JWT token"
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        log.info("Received request to /login endpoint");
        return ResponseEntity.ok(authService.login(request));
    }
}
