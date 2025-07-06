package com.henrique.hbortolim.service.impl;

import com.henrique.hbortolim.dto.chat.ChatMessageDto;
import com.henrique.hbortolim.entity.ChatMessageEntity;
import com.henrique.hbortolim.enums.MessageType;
import com.henrique.hbortolim.mapper.ChatMessageMapper;
import com.henrique.hbortolim.repository.ChatMessageRepository;
import com.henrique.hbortolim.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatMessageServiceImpl.class);
    
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    
    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository, ChatMessageMapper chatMessageMapper) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatMessageMapper = chatMessageMapper;
    }

    @Override
    @Transactional
    public ChatMessageDto save(ChatMessageDto chatMessageDto) {
        logger.info("Saving chat message: {}", chatMessageDto.getContent());
        
        ChatMessageEntity entity = chatMessageMapper.toEntity(chatMessageDto);
        ChatMessageEntity savedEntity = chatMessageRepository.save(entity);
        
        return chatMessageMapper.toDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageDto> findAll() {
        List<ChatMessageEntity> entities = chatMessageRepository.findAllByOrderByTimestampAsc();
        return chatMessageMapper.toDtoList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageDto> findByType(MessageType type) {
        List<ChatMessageEntity> entities = chatMessageRepository.findByTypeOrderByTimestampAsc(type);
        return chatMessageMapper.toDtoList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageDto> findChatMessages() {
        return findByType(MessageType.CHAT);
    }
} 