package com.henrique.hbortolim.handler;

import com.henrique.hbortolim.model.ChatMessage;
import com.henrique.hbortolim.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    private final ObjectMapper objectMapper;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Map<String, String> userNames = new ConcurrentHashMap<>();

    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("New WebSocket connection: {}", session.getId());
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("Received message: {}", payload);

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        // Handle JOIN messages separately to store the username
        if (chatMessage.getType() == MessageType.JOIN) {
            userNames.put(session.getId(), chatMessage.getSender());
            logger.info("User {} joined the chat", chatMessage.getSender());
        }

        // Broadcast message to all connected clients
        broadcastMessage(chatMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("WebSocket connection closed: {}", session.getId());

        // Get the username before removing the session
        String username = userNames.getOrDefault(session.getId(), "someone");

        // Remove the session
        sessions.remove(session.getId());
        userNames.remove(session.getId());

        // Notify other users that someone has left
        try {
            ChatMessage leaveMessage = new ChatMessage();
            leaveMessage.setType(MessageType.LEAVE);
            leaveMessage.setSender(username);
            leaveMessage.setContent(username + " has left the chat");

            broadcastMessage(leaveMessage);
        } catch (Exception e) {
            logger.error("Error sending leave message", e);
        }
    }

    private void broadcastMessage(ChatMessage message) throws IOException {
        String json = objectMapper.writeValueAsString(message);
        TextMessage textMessage = new TextMessage(json);

        for (WebSocketSession webSocketSession : sessions.values()) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(textMessage);
            }
        }
    }
}
