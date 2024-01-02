package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.eyeCategoryDTO.EyeCategoryRequest;
import com.codegym.spb_eyesclinic_project.service.bookingService.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingRestController {

    private final BookingService bookingService;

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody BookingRequest request, @PathVariable Long id) {
        bookingService.update(request, id);
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookingRequest request){
        bookingService.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
