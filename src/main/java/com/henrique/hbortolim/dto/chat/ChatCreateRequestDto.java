package com.henrique.hbortolim.dto.chat;

import com.henrique.hbortolim.constants.ApiConstants;

public class ChatCreateRequestDto {
    private String name;
    private String imageUrl;

    public ChatCreateRequestDto() {
    }

    public ChatCreateRequestDto(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isValid() {
        return name != null && 
               !name.trim().isEmpty() && 
               name.length() <= ApiConstants.Validation.MAX_CHAT_NAME_LENGTH;
    }
} 