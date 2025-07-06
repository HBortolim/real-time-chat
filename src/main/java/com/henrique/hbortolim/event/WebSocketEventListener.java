package com.henrique.hbortolim.event;

import com.henrique.hbortolim.dto.chat.ChatMessageDto;
import com.henrique.hbortolim.enums.MessageType;
import com.henrique.hbortolim.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.ZonedDateTime;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageService chatMessageService;

    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate, ChatMessageService chatMessageService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            logger.info("User Disconnected: {}", username);

            ChatMessageDto chatMessage = new ChatMessageDto();
            chatMessage.setType(MessageType.LEAVE);
            chatMessage.setSender(username);
            chatMessage.setContent(username + " has left the chat");
            chatMessage.setTimestamp(ZonedDateTime.now());

            chatMessage = chatMessageService.save(chatMessage);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
