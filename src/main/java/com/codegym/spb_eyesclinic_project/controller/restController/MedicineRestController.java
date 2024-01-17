package com.codegym.spb_eyesclinic_project.controller.restController;


import com.codegym.spb_eyesclinic_project.domain.EyeCategory;
import com.codegym.spb_eyesclinic_project.domain.Medicine;
import com.codegym.spb_eyesclinic_project.domain.dto.medicineDTO.MedicineResponse;
import com.codegym.spb_eyesclinic_project.repository.MedicineRepository;
import com.codegym.spb_eyesclinic_project.service.eyeCategoryService.EyeCategoryService;
import com.codegym.spb_eyesclinic_project.service.medicine.MedicineService;
import com.codegym.spb_eyesclinic_project.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medicine")
@AllArgsConstructor
public class MedicineRestController {

    private final MedicineRepository medicineRepository;
    private final MedicineService medicineService;

    @GetMapping
    public List<SelectOptionResponse> getSelectOption(){
        return medicineRepository.findAll().stream().map(medicine -> new SelectOptionResponse(medicine.getId().toString(), medicine.getNameMedicine())).collect(Collectors.toList());
    }

    @GetMapping("/all-medicines")
    public List<MedicineResponse> getAllMedicines(){
        return medicineRepository.findAll().stream().map(medicine -> new MedicineResponse(medicine.getId(), medicine.getNameMedicine(), medicine.getPriceMedicine(), medicine.getStockQuantity(), medicine.getType().toString())).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponse> findById(@PathVariable Long id){
        return new ResponseEntity<>(medicineService.findById(id), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<List<MedicineResponse>> findById(@PathVariable Long id) {
//        List<Long> medicineIds = Collections.singletonList(id);
//        List<MedicineResponse> medicineResponses = medicineService.findByIds(medicineIds);
//        return ResponseEntity.ok(medicineResponses);
//    }
}
