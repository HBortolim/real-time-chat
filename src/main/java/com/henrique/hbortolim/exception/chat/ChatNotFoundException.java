package com.henrique.hbortolim.exception.chat;

import com.henrique.hbortolim.exception.common.ResourceNotFoundException;

public class ChatNotFoundException extends ResourceNotFoundException {
    
    public ChatNotFoundException(Long chatId) {
        super("Chat", "id", chatId);
    }
    
    public ChatNotFoundException(String message) {
        super(message);
    }
} 