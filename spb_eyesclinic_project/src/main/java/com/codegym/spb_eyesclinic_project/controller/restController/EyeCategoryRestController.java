package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.domain.EyeCategory;
import com.codegym.spb_eyesclinic_project.domain.dto.eyeCategoryDTO.EyeCategoryRequest;
import com.codegym.spb_eyesclinic_project.service.eyeCategoryService.EyeCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eyeCategory")
@AllArgsConstructor
public class EyeCategoryRestController {
    private final EyeCategoryService eyeCategoryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(eyeCategoryService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        EyeCategory eyeCategory = eyeCategoryService.findById(id);
        return new ResponseEntity<>(eyeCategory,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EyeCategoryRequest request) {
        eyeCategoryService.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody EyeCategoryRequest request, @PathVariable Long id) {
        eyeCategoryService.update(request, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        eyeCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
