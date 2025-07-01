package com.henrique.hbortolim.service;

import com.henrique.hbortolim.dto.ChatMessageDto;
import com.henrique.hbortolim.entity.ChatMessageEntity;
import com.henrique.hbortolim.enums.MessageType;
import com.henrique.hbortolim.mapper.ChatMessageMapper;
import com.henrique.hbortolim.repository.ChatMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatMessageService.class);
    
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    
    public ChatMessageService(ChatMessageRepository chatMessageRepository, ChatMessageMapper chatMessageMapper) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatMessageMapper = chatMessageMapper;
    }

    public ChatMessageDto save(ChatMessageDto chatMessageDto) {
        logger.info("Saving chat message: {}", chatMessageDto.getContent());
        
        ChatMessageEntity entity = chatMessageMapper.toEntity(chatMessageDto);
        ChatMessageEntity savedEntity = chatMessageRepository.save(entity);
        
        return chatMessageMapper.toDto(savedEntity);
    }

    public List<ChatMessageDto> findAll() {
        List<ChatMessageEntity> entities = chatMessageRepository.findAllByOrderByTimestampAsc();
        return chatMessageMapper.toDtoList(entities);
    }

    public List<ChatMessageDto> findByType(MessageType type) {
        List<ChatMessageEntity> entities = chatMessageRepository.findByTypeOrderByTimestampAsc(type);
        return chatMessageMapper.toDtoList(entities);
    }

    public List<ChatMessageDto> findChatMessages() {
        return findByType(MessageType.CHAT);
    }
}