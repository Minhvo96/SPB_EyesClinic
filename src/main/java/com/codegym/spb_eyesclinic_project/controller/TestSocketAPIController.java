package com.codegym.spb_eyesclinic_project.controller;

import com.codegym.spb_eyesclinic_project.domain.socket.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-api")
public class TestSocketAPIController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @PostMapping ("/send")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessage chatMessages){

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(chatMessages.getSender());
        chatMessage.setContent(chatMessages.getContent());

        messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
