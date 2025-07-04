package com.henrique.hbortolim.service;

import com.henrique.hbortolim.dto.UserDto;
import com.henrique.hbortolim.dto.auth.AuthResponseDto;
import com.henrique.hbortolim.dto.auth.LoginRequestDto;
import com.henrique.hbortolim.dto.auth.RegisterRequestDto;
import com.henrique.hbortolim.entity.UserEntity;
import com.henrique.hbortolim.mapper.UserMapper;
import com.henrique.hbortolim.repository.UserRepository;
import com.henrique.hbortolim.security.JwtUtils;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    public AuthService(AuthenticationManager authenticationManager,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      JwtUtils jwtUtils,
                      UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.userMapper = userMapper;
    }

    public AuthResponseDto login(LoginRequestDto loginRequest) {
        logger.info("Attempting login for email: {}", loginRequest.getEmail());

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), 
                loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto userDto = userMapper.toDto(userEntity);

        logger.info("Login successful for email: {}", loginRequest.getEmail());
        return new AuthResponseDto(jwt, userDto);
    }

    @Transactional
    public AuthResponseDto register(RegisterRequestDto registerRequest) {
        logger.info("Attempting registration for email: {}", registerRequest.getEmail());

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        // Create new user account
        UserEntity user = new UserEntity();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setDisplayName(registerRequest.getDisplayName() != null ? 
            registerRequest.getDisplayName() : registerRequest.getUsername());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        // Generate JWT token
        String jwt = jwtUtils.generateTokenFromUsername(savedUser.getEmail());

        UserDto userDto = userMapper.toDto(savedUser);

        logger.info("Registration successful for email: {}", registerRequest.getEmail());
        return new AuthResponseDto(jwt, userDto);
    }
} 