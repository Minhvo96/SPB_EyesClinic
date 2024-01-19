package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.domain.Booking;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingShowDetailResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.eyeCategoryDTO.EyeCategoryRequest;
import com.codegym.spb_eyesclinic_project.domain.socket.ChatMessage;
import com.codegym.spb_eyesclinic_project.service.bookingService.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingRestController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(bookingService.getAll(),HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ) {
        return new ResponseEntity<>(bookingService.getById(id),HttpStatus.OK);
    }

    @GetMapping("/examining")
    public ResponseEntity<?> getByStatus(){
        EStatus string = EStatus.EXAMINING;
        return new ResponseEntity<>(bookingService.getByStatus(string),HttpStatus.OK);
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<BookingShowDetailResponse> findBookingDetail(@PathVariable Long id) {
        return new ResponseEntity<>(bookingService.findBookingShowDetailById(id), HttpStatus.OK);
    }

    @PostMapping("/waiting")
    public ResponseEntity<?> getByStatusWaiting(@RequestBody BookingRequest request){
        EStatus string = EStatus.WAITING;
        String date = request.getDateBooking();
        return new ResponseEntity<>(bookingService.getByStatusWaiting(string, date),HttpStatus.OK);
    }

    @PostMapping("/waiting-examining")
    public ResponseEntity<?> getByStatusWaitingOrExamining(@RequestBody BookingRequest request){
        EStatus waiting = EStatus.WAITING;
        EStatus examining = EStatus.EXAMINING;
        String date = request.getDateBooking();
        return new ResponseEntity<>(bookingService.getByStatusWaitingOrExamining(waiting, examining, date),HttpStatus.OK);
    }

    @PostMapping("/pending")
    public ResponseEntity<?> getByStatusPending(@RequestBody BookingRequest request){
        EStatus string = EStatus.PENDING;
        String date = request.getDateBooking();
        return new ResponseEntity<>(bookingService.getByStatusPending(string, date),HttpStatus.OK);

    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody BookingRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(bookingService.update(request, id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookingRequest request){
        bookingService.create(request);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender("Customer");
        chatMessage.setContent("Vừa có khách đặt lịch khám vào: " + request.getTimeBooking() + " ngày " + request.getDateBooking());
        messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

<<<<<<< HEAD
    @GetMapping("/user/{phone}")
    public List<Booking> getBookingsByUserPhone(@PathVariable String phone) {
        return bookingService.getBookingsByUserPhone(phone);
    }
=======

>>>>>>> 10634501aaeec50d4797bc6983fe3a032b034026
}
