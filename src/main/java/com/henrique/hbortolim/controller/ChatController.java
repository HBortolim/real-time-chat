package com.henrique.hbortolim.controller;

import com.henrique.hbortolim.dto.ChatDto;
import com.henrique.hbortolim.security.UserPrincipal;
import com.henrique.hbortolim.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<List<ChatDto>> listNonArchivedChatsByUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        List<ChatDto> chats = chatService.getNonArchivedChatsByUserId(userId);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatDto>> listAllChatsByUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        List<ChatDto> chats = chatService.getAllChatsByUserId(userId);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/archived")
    public ResponseEntity<List<ChatDto>> listArchivedChatsByUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        List<ChatDto> chats = chatService.getArchivedChatsByUserId(userId);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDto> getChatById(@PathVariable Long chatId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        ChatDto chat = chatService.getChatById(chatId, userId);
        return ResponseEntity.ok(chat);
    }

    @PostMapping
    public ResponseEntity<ChatDto> createChat(@RequestBody ChatDto chatDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        ChatDto createdChat = chatService.createChat(chatDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChat);
    }

    @PutMapping("/{chatId}")
    public ResponseEntity<ChatDto> updateChat(@PathVariable Long chatId, @RequestBody ChatDto chatDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        ChatDto updatedChat = chatService.updateChat(chatId, chatDto, userId);
        return ResponseEntity.ok(updatedChat);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long chatId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        chatService.deleteChat(chatId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{chatId}/archive")
    public ResponseEntity<ChatDto> archiveChat(@PathVariable Long chatId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        ChatDto archivedChat = chatService.archiveChat(chatId, userId);
        return ResponseEntity.ok(archivedChat);
    }

    @PutMapping("/{chatId}/unarchive")
    public ResponseEntity<ChatDto> unarchiveChat(@PathVariable Long chatId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        ChatDto unarchivedChat = chatService.unarchiveChat(chatId, userId);
        return ResponseEntity.ok(unarchivedChat);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getChatCount(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        long count = chatService.getChatCountByUserId(userId);
        return ResponseEntity.ok(count);
    }
} 