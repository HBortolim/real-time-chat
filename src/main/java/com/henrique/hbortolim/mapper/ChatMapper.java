package com.henrique.hbortolim.mapper;

import com.henrique.hbortolim.dto.ChatDto;
import com.henrique.hbortolim.entity.ChatEntity;
import com.henrique.hbortolim.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatMapper {

    public ChatDto toDto(ChatEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ChatDto(
            entity.getId(),
            entity.getUser() != null ? entity.getUser().getId() : null,
            entity.getName(),
            entity.getImageUrl(),
            entity.getCreatedAt(),
            entity.getIsArchived()
        );
    }

    public ChatEntity toEntity(ChatDto dto) {
        if (dto == null) {
            return null;
        }

        ChatEntity entity = new ChatEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setImageUrl(dto.getImageUrl());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setIsArchived(dto.getIsArchived());

        // Note: UserEntity should be set by the service layer
        return entity;
    }

    public ChatEntity toEntity(ChatDto dto, UserEntity user) {
        if (dto == null) {
            return null;
        }

        ChatEntity entity = toEntity(dto);
        entity.setUser(user);
        return entity;
    }

    public List<ChatDto> toDtoList(List<ChatEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ChatEntity> toEntityList(List<ChatDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(ChatDto dto, ChatEntity entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getImageUrl() != null) {
            entity.setImageUrl(dto.getImageUrl());
        }
        if (dto.getIsArchived() != null) {
            entity.setIsArchived(dto.getIsArchived());
        }
    }
} 