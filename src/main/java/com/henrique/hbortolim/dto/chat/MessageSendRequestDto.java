package com.henrique.hbortolim.dto.chat;

import com.henrique.hbortolim.enums.MessageType;

public class MessageSendRequestDto {
    private String content;
    private MessageType type = MessageType.CHAT;

    public MessageSendRequestDto() {
    }

    public MessageSendRequestDto(String content, MessageType type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public boolean isValid() {
        return content != null && 
               !content.trim().isEmpty() && 
               content.length() <= 1000 &&
               type != null;
    }
} 