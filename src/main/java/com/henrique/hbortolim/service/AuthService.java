package com.henrique.hbortolim.service;

import com.henrique.hbortolim.dto.auth.AuthResponseDto;
import com.henrique.hbortolim.dto.auth.LoginRequestDto;
import com.henrique.hbortolim.dto.auth.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto login(LoginRequestDto loginRequest);

    AuthResponseDto register(RegisterRequestDto registerRequest);
} 