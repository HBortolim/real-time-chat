package com.henrique.hbortolim.service;

import com.henrique.hbortolim.dto.ChatDto;
import com.henrique.hbortolim.entity.ChatEntity;
import com.henrique.hbortolim.entity.UserEntity;
import com.henrique.hbortolim.mapper.ChatMapper;
import com.henrique.hbortolim.repository.ChatRepository;
import com.henrique.hbortolim.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatService {

    List<ChatDto> getNonArchivedChatsByUserId(Long userId);

    List<ChatDto> getAllChatsByUserId(Long userId);

    List<ChatDto> getArchivedChatsByUserId(Long userId);

    ChatDto getChatById(Long chatId, Long userId);

    ChatDto createChat(ChatDto chatDto, Long userId);

    ChatDto updateChat(Long chatId, ChatDto chatDto, Long userId);

    void deleteChat(Long chatId, Long userId);

    ChatDto archiveChat(Long chatId, Long userId);

    ChatDto unarchiveChat(Long chatId, Long userId);

    long getChatCountByUserId(Long userId);
}