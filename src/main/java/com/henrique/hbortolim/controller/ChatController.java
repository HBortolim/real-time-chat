package com.henrique.hbortolim.controller;

import com.henrique.hbortolim.constants.ApiConstants;
import com.henrique.hbortolim.dto.chat.ChatDto;
import com.henrique.hbortolim.dto.common.ApiResponseDto;
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
@RequestMapping(ApiConstants.Endpoints.CHAT_PREFIX)
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ChatDto>>> listChatsByUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        List<ChatDto> chats = chatService.getNonArchivedChatsByUserId(userId);
        return ResponseEntity.ok(ApiResponseDto.success("Chats retrieved successfully", chats));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto<List<ChatDto>>> listAllChatsByUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        List<ChatDto> chats = chatService.getAllChatsByUserId(userId);
        return ResponseEntity.ok(ApiResponseDto.success("All chats retrieved successfully", chats));
    }

    @GetMapping("/archived")
    public ResponseEntity<ApiResponseDto<List<ChatDto>>> listArchivedChatsByUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        List<ChatDto> chats = chatService.getArchivedChatsByUserId(userId);
        return ResponseEntity.ok(ApiResponseDto.success("Archived chats retrieved successfully", chats));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ApiResponseDto<ChatDto>> getChatById(@PathVariable Long chatId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        ChatDto chat = chatService.getChatById(chatId, userId);
        return ResponseEntity.ok(ApiResponseDto.success("Chat retrieved successfully", chat));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<ChatDto>> createChat(@RequestBody ChatDto chatDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        ChatDto createdChat = chatService.createChat(chatDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Chat created successfully", createdChat));
    }

    @PutMapping("/{chatId}")
    public ResponseEntity<ApiResponseDto<ChatDto>> updateChat(@PathVariable Long chatId, @RequestBody ChatDto chatDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        ChatDto updatedChat = chatService.updateChat(chatId, chatDto, userId);
        return ResponseEntity.ok(ApiResponseDto.success("Chat updated successfully", updatedChat));
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteChat(@PathVariable Long chatId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        chatService.deleteChat(chatId, userId);
        return ResponseEntity.ok(ApiResponseDto.success("Chat deleted successfully", null));
    }

    @PutMapping("/{chatId}/archive")
    public ResponseEntity<ApiResponseDto<ChatDto>> archiveChat(@PathVariable Long chatId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        ChatDto archivedChat = chatService.archiveChat(chatId, userId);
        return ResponseEntity.ok(ApiResponseDto.success("Chat archived successfully", archivedChat));
    }

    @PutMapping("/{chatId}/unarchive")
    public ResponseEntity<ApiResponseDto<ChatDto>> unarchiveChat(@PathVariable Long chatId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        ChatDto unarchivedChat = chatService.unarchiveChat(chatId, userId);
        return ResponseEntity.ok(ApiResponseDto.success("Chat unarchived successfully", unarchivedChat));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponseDto<Long>> getChatCount(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        long count = chatService.getChatCountByUserId(userId);
        return ResponseEntity.ok(ApiResponseDto.success("Chat count retrieved successfully", count));
    }
} 