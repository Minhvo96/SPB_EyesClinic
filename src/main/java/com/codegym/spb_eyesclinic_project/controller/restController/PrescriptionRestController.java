package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.domain.dto.eyeCategoryDTO.EyeCategoryRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionRequest;
import com.codegym.spb_eyesclinic_project.domain.socket.ChatMessage;
import com.codegym.spb_eyesclinic_project.service.prescriptionService.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescription")
@AllArgsConstructor

public class PrescriptionRestController {
    private final PrescriptionService prescriptionService;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PrescriptionRequest request) {
        prescriptionService.create(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody PrescriptionRequest request, @PathVariable Long id) {
        prescriptionService.updatePrescription(request, id);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender("DOCTOR");
        chatMessage.setContent("Đã khám cho bệnh nhân xong, chờ thanh toán!");
        messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<?> getPrescriptionByBookingId(@PathVariable Long id) {
        return new ResponseEntity<>(prescriptionService.getPrescriptionByBookingId(id),HttpStatus.OK);
    }

}
