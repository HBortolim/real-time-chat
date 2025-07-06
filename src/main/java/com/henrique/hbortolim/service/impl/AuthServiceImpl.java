package com.henrique.hbortolim.service.impl;

import com.henrique.hbortolim.constants.ApiConstants;
import com.henrique.hbortolim.dto.UserDto;
import com.henrique.hbortolim.dto.auth.AuthResponseDto;
import com.henrique.hbortolim.dto.auth.LoginRequestDto;
import com.henrique.hbortolim.dto.auth.RegisterRequestDto;
import com.henrique.hbortolim.entity.UserEntity;
import com.henrique.hbortolim.exception.auth.UserAlreadyExistsException;
import com.henrique.hbortolim.exception.common.ResourceNotFoundException;
import com.henrique.hbortolim.mapper.UserMapper;
import com.henrique.hbortolim.repository.UserRepository;
import com.henrique.hbortolim.security.JwtUtils;
import com.henrique.hbortolim.service.AuthService;
import com.henrique.hbortolim.util.ValidationUtils;

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
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
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

    @Override
    @Transactional(readOnly = true)
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
            .orElseThrow(() -> new ResourceNotFoundException("User", "email", loginRequest.getEmail()));

        UserDto userDto = userMapper.toDto(userEntity);

        logger.info("Login successful for email: {}", loginRequest.getEmail());
        return new AuthResponseDto(jwt, userDto);
    }

    @Override
    @Transactional
    public AuthResponseDto register(RegisterRequestDto registerRequest) {
        logger.info("Attempting registration for email: {}", registerRequest.getEmail());

        validateRegistrationRequest(registerRequest);

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("email", registerRequest.getEmail());
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("username", registerRequest.getUsername());
        }

        UserEntity user = new UserEntity();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setDisplayName(registerRequest.getDisplayName() != null ? 
            registerRequest.getDisplayName() : registerRequest.getUsername());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        String jwt = jwtUtils.generateTokenFromUsername(savedUser.getEmail());

        UserDto userDto = userMapper.toDto(savedUser);

        logger.info("Registration successful for email: {}", registerRequest.getEmail());
        return new AuthResponseDto(jwt, userDto);
    }

    private void validateRegistrationRequest(RegisterRequestDto request) {
        if (!ValidationUtils.isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        if (!ValidationUtils.isValidUsername(request.getUsername())) {
            throw new IllegalArgumentException("Invalid username format");
        }
        
        if (!ValidationUtils.isValidPassword(request.getPassword())) {
            throw new IllegalArgumentException("Password must be between " + 
                ApiConstants.Validation.MIN_PASSWORD_LENGTH + " and " + 
                ApiConstants.Validation.MAX_PASSWORD_LENGTH + " characters");
        }
    }
} 