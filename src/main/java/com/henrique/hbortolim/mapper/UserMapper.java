package com.henrique.hbortolim.mapper;

import com.henrique.hbortolim.dto.UserDto;
import com.henrique.hbortolim.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        dto.setDisplayName(entity.getDisplayName());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastSeenAt(entity.getLastSeenAt());
        dto.setActive(entity.isActive());
        dto.setEmailVerified(entity.isEmailVerified());
        // Password is intentionally not mapped to DTO for security

        return dto;
    }

    public UserEntity toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setDisplayName(dto.getDisplayName());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setRole(dto.getRole());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setLastSeenAt(dto.getLastSeenAt());
        entity.setActive(dto.isActive());
        entity.setEmailVerified(dto.isEmailVerified());
        // Password should be handled separately for security

        return entity;
    }

    public List<UserDto> toDtoList(List<UserEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<UserEntity> toEntityList(List<UserDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 