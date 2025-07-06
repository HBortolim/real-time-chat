package com.henrique.hbortolim.mapper;

import com.henrique.hbortolim.dto.chat.ChatCreateRequestDto;
import com.henrique.hbortolim.dto.chat.ChatDto;
import com.henrique.hbortolim.entity.ChatEntity;
import com.henrique.hbortolim.entity.UserEntity;
import com.henrique.hbortolim.util.MapperUtils;
import com.henrique.hbortolim.util.DateUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatMapper {

    public ChatDto toDto(ChatEntity entity) {
        return MapperUtils.mapNullSafe(entity, this::entityToDto);
    }

    public ChatEntity toEntity(ChatDto dto) {
        return MapperUtils.mapNullSafe(dto, this::dtoToEntity);
    }

    public ChatEntity toEntity(ChatDto dto, UserEntity user) {
        if (dto == null) {
            return null;
        }

        ChatEntity entity = dtoToEntity(dto);
        entity.setUser(user);
        return entity;
    }

    public ChatDto fromCreateRequest(ChatCreateRequestDto request) {
        return MapperUtils.mapNullSafe(request, this::createRequestToDto);
    }

    public List<ChatDto> toDtoList(List<ChatEntity> entities) {
        return MapperUtils.mapList(entities, this::entityToDto);
    }

    public List<ChatEntity> toEntityList(List<ChatDto> dtos) {
        return MapperUtils.mapList(dtos, this::dtoToEntity);
    }

    public void updateEntityFromDto(ChatDto dto, ChatEntity entity) {
        if (dto == null || entity == null) {
            return;
        }

        MapperUtils.updateStringIfNotBlank(dto.getName(), () -> entity.setName(dto.getName()));
        MapperUtils.updateIfNotNull(dto.getImageUrl(), () -> entity.setImageUrl(dto.getImageUrl()));
        MapperUtils.updateIfNotNull(dto.getIsArchived(), () -> entity.setIsArchived(dto.getIsArchived()));
    }

    private ChatDto entityToDto(ChatEntity entity) {
        return new ChatDto(
            entity.getId(),
            MapperUtils.mapNullSafe(entity.getUser(), UserEntity::getId),
            entity.getName(),
            entity.getImageUrl(),
            entity.getCreatedAt(),
            MapperUtils.getOrDefault(entity.getIsArchived(), false)
        );
    }

    private ChatEntity dtoToEntity(ChatDto dto) {
        ChatEntity entity = new ChatEntity();
        entity.setId(dto.getId());
        entity.setName(MapperUtils.trimToNull(dto.getName()));
        entity.setImageUrl(MapperUtils.trimToNull(dto.getImageUrl()));
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setIsArchived(MapperUtils.getOrDefault(dto.getIsArchived(), false));
        return entity;
    }

    private ChatDto createRequestToDto(ChatCreateRequestDto request) {
        return new ChatDto(
            null, // ID will be generated
            null, // User ID will be set by service
            MapperUtils.trimToNull(request.getName()),
            MapperUtils.trimToNull(request.getImageUrl()),
            DateUtils.now(),
            false // New chats are not archived
        );
    }
} 