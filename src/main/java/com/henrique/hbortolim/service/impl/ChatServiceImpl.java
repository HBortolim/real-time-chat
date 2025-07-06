package com.henrique.hbortolim.service.impl;

import com.henrique.hbortolim.dto.ChatDto;
import com.henrique.hbortolim.entity.ChatEntity;
import com.henrique.hbortolim.entity.UserEntity;
import com.henrique.hbortolim.mapper.ChatMapper;
import com.henrique.hbortolim.repository.ChatRepository;
import com.henrique.hbortolim.repository.UserRepository;
import com.henrique.hbortolim.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    public ChatServiceImpl(ChatRepository chatRepository, UserRepository userRepository, ChatMapper chatMapper) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatMapper = chatMapper;
    }

    @Override
    public List<ChatDto> getNonArchivedChatsByUserId(Long userId) {
        logger.info("Getting chats for user ID: {}", userId);
        List<ChatEntity> chats = chatRepository.findByUserIdAndIsArchivedFalseOrderByCreatedAtDesc(userId);
        return chatMapper.toDtoList(chats);
    }

    @Override
    public List<ChatDto> getAllChatsByUserId(Long userId) {
        logger.info("Getting all chats (including archived) for user ID: {}", userId);
        List<ChatEntity> chats = chatRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return chatMapper.toDtoList(chats);
    }

    @Override
    public List<ChatDto> getArchivedChatsByUserId(Long userId) {
        logger.info("Getting archived chats for user ID: {}", userId);
        List<ChatEntity> chats = chatRepository.findByUserIdAndIsArchivedTrueOrderByCreatedAtDesc(userId);
        return chatMapper.toDtoList(chats);
    }

    @Override
    public ChatDto getChatById(Long chatId, Long userId) {
        logger.info("Getting chat ID: {} for user ID: {}", chatId, userId);
        ChatEntity chat = chatRepository.findByUserIdAndId(userId, chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found or does not belong to user"));
        return chatMapper.toDto(chat);
    }

    @Override
    @Transactional
    public ChatDto createChat(ChatDto chatDto, Long userId) {
        logger.info("Creating new chat for user ID: {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatEntity chatEntity = chatMapper.toEntity(chatDto, user);
        ChatEntity savedChat = chatRepository.save(chatEntity);

        logger.info("Chat created with ID: {}", savedChat.getId());
        return chatMapper.toDto(savedChat);
    }

    @Override
    @Transactional
    public ChatDto updateChat(Long chatId, ChatDto chatDto, Long userId) {
        logger.info("Updating chat ID: {} for user ID: {}", chatId, userId);

        ChatEntity existingChat = chatRepository.findByUserIdAndId(userId, chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found or does not belong to user"));

        chatMapper.updateEntityFromDto(chatDto, existingChat);
        ChatEntity updatedChat = chatRepository.save(existingChat);

        logger.info("Chat updated with ID: {}", updatedChat.getId());
        return chatMapper.toDto(updatedChat);
    }

    @Override
    @Transactional
    public void deleteChat(Long chatId, Long userId) {
        logger.info("Deleting chat ID: {} for user ID: {}", chatId, userId);

        ChatEntity chat = chatRepository.findByUserIdAndId(userId, chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found or does not belong to user"));

        chatRepository.delete(chat);
        logger.info("Chat deleted with ID: {}", chatId);
    }

    @Override
    @Transactional
    public ChatDto archiveChat(Long chatId, Long userId) {
        logger.info("Archiving chat ID: {} for user ID: {}", chatId, userId);

        ChatEntity chat = chatRepository.findByUserIdAndId(userId, chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found or does not belong to user"));

        chat.setIsArchived(true);
        ChatEntity archivedChat = chatRepository.save(chat);

        logger.info("Chat archived with ID: {}", archivedChat.getId());
        return chatMapper.toDto(archivedChat);
    }

    @Override
    @Transactional
    public ChatDto unarchiveChat(Long chatId, Long userId) {
        logger.info("Unarchiving chat ID: {} for user ID: {}", chatId, userId);

        ChatEntity chat = chatRepository.findByUserIdAndId(userId, chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found or does not belong to user"));

        chat.setIsArchived(false);
        ChatEntity unarchivedChat = chatRepository.save(chat);

        logger.info("Chat unarchived with ID: {}", unarchivedChat.getId());
        return chatMapper.toDto(unarchivedChat);
    }

    @Override
    public long getChatCountByUserId(Long userId) {
        return chatRepository.countByUserIdAndIsArchivedFalse(userId);
    }
} 