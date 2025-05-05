package com.henrique.hbortolim.controller;

import com.henrique.hbortolim.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.ZonedDateTime;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        logger.info("Received message: {}", chatMessage.getContent());

        if (chatMessage.getTimestamp() == null) {
            chatMessage.setTimestamp(ZonedDateTime.now());
        }
        
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, 
                               SimpMessageHeaderAccessor headerAccessor) {
        logger.info("User {} joined the chat", chatMessage.getSender());

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        if (chatMessage.getTimestamp() == null) {
            chatMessage.setTimestamp(ZonedDateTime.now());
        }
        
        return chatMessage;
    }
}