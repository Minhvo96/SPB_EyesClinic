package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.domain.dto.eyeCategoryDTO.EyeCategoryRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionRequest;
import com.codegym.spb_eyesclinic_project.service.prescriptionService.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescription")
@AllArgsConstructor
public class PrescriptionRestController {
    private final PrescriptionService prescriptionService;

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

}
