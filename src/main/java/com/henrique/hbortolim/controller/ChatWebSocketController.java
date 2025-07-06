package com.henrique.hbortolim.controller;

import com.henrique.hbortolim.dto.chat.ChatMessageDto;
import com.henrique.hbortolim.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * WebSocket controller for real-time chat messaging
 */
@Controller
public class ChatWebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketController.class);
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDto sendMessage(@Payload ChatMessageDto chatMessage) {
        logger.info("Received message: {}", chatMessage.getContent());

        if (chatMessage.getTimestamp() == null) {
            chatMessage.setTimestamp(ZonedDateTime.now());
        }

        return chatMessageService.save(chatMessage);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDto addUser(@Payload ChatMessageDto chatMessage, 
                               SimpMessageHeaderAccessor headerAccessor) {
        logger.info("User {} joined the chat", chatMessage.getSender());

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        if (chatMessage.getTimestamp() == null) {
            chatMessage.setTimestamp(ZonedDateTime.now());
        }

        ChatMessageDto savedMessage = chatMessageService.save(chatMessage);

        sendChatHistoryToUser(chatMessage.getSender());

        return savedMessage;
    }

    private void sendChatHistoryToUser(String username) {
        logger.info("Sending chat history to user: {}", username);

        List<ChatMessageDto> chatHistory = chatMessageService.findChatMessages();

        messagingTemplate.convertAndSendToUser(
            username,
            "/queue/history",
            chatHistory
        );
    }
}
