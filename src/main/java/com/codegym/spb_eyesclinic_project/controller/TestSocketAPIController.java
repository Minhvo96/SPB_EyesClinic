package com.codegym.spb_eyesclinic_project.controller;

import com.codegym.spb_eyesclinic_project.domain.socket.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-api")
public class TestSocketAPIController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @GetMapping("/send")
    public ResponseEntity<?> sendMessage(){

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender("SERVER");
        chatMessage.setContent("Server gửi về nè: " + Math.random());

        messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
