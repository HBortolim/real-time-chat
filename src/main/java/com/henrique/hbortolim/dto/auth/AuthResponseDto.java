package com.henrique.hbortolim.dto.auth;

import com.henrique.hbortolim.dto.UserDto;

public class AuthResponseDto {
    
    private String token;
    private String tokenType = "Bearer";
    private UserDto user;

    public AuthResponseDto() {}

    public AuthResponseDto(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
} 