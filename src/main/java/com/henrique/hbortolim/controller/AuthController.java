package com.henrique.hbortolim.controller;

import com.henrique.hbortolim.constants.ApiConstants;
import com.henrique.hbortolim.dto.auth.AuthResponseDto;
import com.henrique.hbortolim.dto.auth.LoginRequestDto;
import com.henrique.hbortolim.dto.auth.RegisterRequestDto;
import com.henrique.hbortolim.dto.common.ApiResponseDto;
import com.henrique.hbortolim.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.Endpoints.AUTH_PREFIX)
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<AuthResponseDto>> login(@RequestBody LoginRequestDto loginRequest) {
        logger.info("Login request received for email: {}", loginRequest.getEmail());
        AuthResponseDto authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponseDto.success("Login successful", authResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<AuthResponseDto>> register(@RequestBody RegisterRequestDto registerRequest) {
        logger.info("Registration request received for email: {}", registerRequest.getEmail());
        AuthResponseDto authResponse = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Registration successful", authResponse));
    }
} 