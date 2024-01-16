package com.codegym.spb_eyesclinic_project.service.medicine;


import com.codegym.spb_eyesclinic_project.domain.Booking;
import com.codegym.spb_eyesclinic_project.domain.EyeCategory;
import com.codegym.spb_eyesclinic_project.domain.Medicine;
import com.codegym.spb_eyesclinic_project.domain.dto.medicineDTO.MedicineResponse;
import com.codegym.spb_eyesclinic_project.repository.EyeCategoryRepository;
import com.codegym.spb_eyesclinic_project.repository.MedicineRepository;
import com.codegym.spb_eyesclinic_project.service.response.SelectOptionResponse;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public List<SelectOptionResponse> findAll() {
        return medicineRepository.findAll().stream()
                .map(medicine -> new SelectOptionResponse(medicine.getId().toString(), medicine.getNameMedicine()))
                .collect(Collectors.toList());
    }

    public MedicineResponse findById(Long id) {
        Medicine medicine = medicineRepository.findById(id).orElse(new Medicine());
        return AppUtils.mapper.map(medicine, MedicineResponse.class);
    }



//    public List<MedicineResponse> findByIds(List<Long> medicineIds) {
//        List<Object[]> results = medicineRepository.findPricesByIds(medicineIds);
//        return convertToMedicineResponses(results);
//    }
//
//    private List<MedicineResponse> convertToMedicineResponses(List<Object[]> results) {
//        List<MedicineResponse> responses = new ArrayList<>();
//        for (Object[] result : results) {
//            Long id = (Long) result[0];
//            BigDecimal price = (BigDecimal) result[1];
//
//            MedicineResponse response = new MedicineResponse();
//            response.setId(id);
//            response.setPriceMedicine(price);
//            responses.add(response);
//        }
//        return responses;
//    }
}
