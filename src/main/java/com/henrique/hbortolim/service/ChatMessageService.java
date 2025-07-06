package com.henrique.hbortolim.service;

import com.henrique.hbortolim.dto.chat.ChatMessageDto;
import com.henrique.hbortolim.entity.ChatMessageEntity;
import com.henrique.hbortolim.enums.MessageType;
import com.henrique.hbortolim.mapper.ChatMessageMapper;
import com.henrique.hbortolim.repository.ChatMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ChatMessageService {

    ChatMessageDto save(ChatMessageDto chatMessageDto);

    List<ChatMessageDto> findAll();

    List<ChatMessageDto> findByType(MessageType type);

    List<ChatMessageDto> findChatMessages();
}