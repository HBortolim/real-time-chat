package com.henrique.hbortolim.controller;

import org.springframework.web.bind.annotation.RestController;

import com.henrique.hbortolim.service.ChatMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@RestController
public class ChatRestController {

    private final ChatMessageService chatMessageService;

    public ChatRestController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    //TODO: Implement CRUD operations for chats
    
}
