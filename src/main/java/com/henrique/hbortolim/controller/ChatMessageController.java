package com.henrique.hbortolim.controller;

import com.henrique.hbortolim.constants.ApiConstants;
import com.henrique.hbortolim.dto.chat.ChatMessageDto;
import com.henrique.hbortolim.dto.common.ApiResponseDto;
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
@RequestMapping(ApiConstants.Endpoints.CHAT_PREFIX + "/{chatId}/messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ChatMessageDto>>> getChatMessages(
            @PathVariable Long chatId, 
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        List<ChatMessageDto> messages = chatMessageService.findChatMessages();
        return ResponseEntity.ok(ApiResponseDto.success("Messages retrieved successfully", messages));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<ChatMessageDto>> sendMessage(
            @PathVariable Long chatId, 
            @RequestBody ChatMessageDto messageDto, 
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        messageDto.setSender(userPrincipal.getUsername());
        ChatMessageDto savedMessage = chatMessageService.save(messageDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Message sent successfully", savedMessage));
    }
} 