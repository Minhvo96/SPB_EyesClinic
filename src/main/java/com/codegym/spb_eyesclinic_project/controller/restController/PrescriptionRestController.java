package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingShowDetailResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.eyeCategoryDTO.EyeCategoryRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionShowDetailResponse;
import com.codegym.spb_eyesclinic_project.service.prescriptionService.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/prescription")
@AllArgsConstructor

public class PrescriptionRestController {
    private final PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<Page<PrescriptionResponse>> getPrescriptions(@PageableDefault(size = 5) Pageable pageable,
                                                                       @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(prescriptionService.getAll(pageable, search), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody PrescriptionRequest request) {
        prescriptionService.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody PrescriptionRequest request, @PathVariable Long id) {
        prescriptionService.update(request, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PrescriptionShowDetailResponse> findPrescriptionDetail(@PathVariable Long id){
        return new ResponseEntity<>(prescriptionService.findShowDetailById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPrescriptionByIdBooking(@PathVariable Long id){
        return new ResponseEntity<>(prescriptionService.findByIdBooking(id), HttpStatus.OK);
    }
}
