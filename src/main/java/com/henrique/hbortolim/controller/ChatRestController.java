package com.henrique.hbortolim.controller;

import org.springframework.web.bind.annotation.RestController;

import com.henrique.hbortolim.dto.ChatMessageDto;
import com.henrique.hbortolim.security.AuthenticationUtils;
import com.henrique.hbortolim.security.UserPrincipal;
import com.henrique.hbortolim.service.ChatMessageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatRestController {

    private final ChatMessageService chatMessageService;

    public ChatRestController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    // 
    @GetMapping
    public List<ChatMessageDto> getChatsByUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        return chatMessageService.getChatsByUserId(userId);
    }

    // Approach 2: Using AuthenticationUtils (Good for multiple controllers)
    // @GetMapping("/api/chats/my-messages")
    // public List<ChatMessageDto> getMyMessages() {
    //     String username = AuthenticationUtils.getCurrentUsername();
    //     return chatMessageService.findChatMessagesBySender(username);
    // }

    // Example of getting user ID if needed for other operations
    // @GetMapping("/api/chats/user-info")
    // public String getUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
    //     Long userId = userPrincipal.getId();
    //     String email = userPrincipal.getEmail();
    //     String username = userPrincipal.getUsername();
        
    //     return String.format("User ID: %d, Email: %s, Username: %s", userId, email, username);
    // }
}
