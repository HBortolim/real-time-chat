package com.henrique.hbortolim.controller;

import com.henrique.hbortolim.dto.ChatMessageDto;
import com.henrique.hbortolim.security.UserPrincipal;
import com.henrique.hbortolim.service.ChatMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats/{chatId}/messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping
    public ResponseEntity<List<ChatMessageDto>> getChatMessages(@PathVariable Long chatId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        List<ChatMessageDto> messages = chatMessageService.findChatMessages();
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<ChatMessageDto> sendMessage(@PathVariable Long chatId, @RequestBody ChatMessageDto messageDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        messageDto.setSender(userPrincipal.getUsername());
        ChatMessageDto savedMessage = chatMessageService.save(messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }
} 