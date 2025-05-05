package com.henrique.hbortolim.service;

import com.henrique.hbortolim.model.ChatMessage;
import com.henrique.hbortolim.model.MessageType;
import com.henrique.hbortolim.repository.ChatMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatMessageService.class);
    
    private final ChatMessageRepository chatMessageRepository;
    
    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessage save(ChatMessage chatMessage) {
        logger.info("Saving chat message: {}", chatMessage.getContent());
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findAll() {
        return chatMessageRepository.findAllByOrderByTimestampAsc();
    }

    public List<ChatMessage> findByType(MessageType type) {
        return chatMessageRepository.findByTypeOrderByTimestampAsc(type);
    }

    public List<ChatMessage> findChatMessages() {
        return findByType(MessageType.CHAT);
    }
}