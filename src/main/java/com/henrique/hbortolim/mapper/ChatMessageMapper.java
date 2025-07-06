package com.henrique.hbortolim.mapper;

import com.henrique.hbortolim.dto.chat.ChatMessageDto;
import com.henrique.hbortolim.entity.ChatMessageEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatMessageMapper {

    public ChatMessageDto toDto(ChatMessageEntity entity) {
        if (entity == null) {
            return null;
        }

        ChatMessageDto dto = new ChatMessageDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setSender(entity.getSender());
        dto.setContent(entity.getContent());
        dto.setTimestamp(entity.getTimestamp());

        return dto;
    }

    public ChatMessageEntity toEntity(ChatMessageDto dto) {
        if (dto == null) {
            return null;
        }

        ChatMessageEntity entity = new ChatMessageEntity();
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setSender(dto.getSender());
        entity.setContent(dto.getContent());
        entity.setTimestamp(dto.getTimestamp());

        return entity;
    }

    public List<ChatMessageDto> toDtoList(List<ChatMessageEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ChatMessageEntity> toEntityList(List<ChatMessageDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 