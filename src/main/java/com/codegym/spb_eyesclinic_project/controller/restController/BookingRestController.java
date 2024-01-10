package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.eyeCategoryDTO.EyeCategoryRequest;
import com.codegym.spb_eyesclinic_project.service.bookingService.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingRestController {

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

    @PostMapping("/waiting")
    public ResponseEntity<?> getByStatusWaiting(@RequestBody BookingRequest request){
        EStatus string = EStatus.WAITING;
        String date = request.getDateBooking();
        return new ResponseEntity<>(bookingService.getByStatusWaiting(string, date),HttpStatus.OK);
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
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
