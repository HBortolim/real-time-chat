package com.henrique.hbortolim.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;

public class ChatDto {
    private Long id;
    private Long userId;
    private String name;
    private String imageUrl;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime createdAt;
    
    private Boolean isArchived;

    public ChatDto() {
    }

    public ChatDto(Long id, Long userId, String name, String imageUrl, ZonedDateTime createdAt, Boolean isArchived) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.isArchived = isArchived;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }
} 