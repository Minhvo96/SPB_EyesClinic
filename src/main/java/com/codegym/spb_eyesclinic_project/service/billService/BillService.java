package com.codegym.spb_eyesclinic_project.service.billService;

import com.codegym.spb_eyesclinic_project.domain.*;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.medicineDTO.MedicineResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionShowDetailResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.response.OptionResponse;
import com.codegym.spb_eyesclinic_project.repository.*;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BillService {
    private final BillRepository billRepository;

    private final PrescriptionRepository prescriptionRepository;

    private final StaffRepository staffRepository;

    private final EyeCategoryRepository eyeCategoryRepository;

    private final MedicineRepository medicineRepository;


   public void create(BillRequest request) {
        Bill bill = AppUtils.mapper.map(request, Bill.class);
        Prescription prescription = prescriptionRepository.findById(Long.valueOf(request.getIdPrescription())).orElse(new Prescription());
        Staff receptionist = staffRepository.findById(Long.valueOf(request.getIdReceptionist())).orElse(new Staff());
        bill.setPrescription(prescription);
        bill.setReceptionist(receptionist);
        bill.setDateDisease(LocalDateTime.now());
        billRepository.save(bill);

    }

//    public void create(BillRequest request) {
//        Bill bill = AppUtils.mapper.map(request, Bill.class);
//        Prescription prescription = prescriptionRepository.findById(Long.valueOf(request.getIdPrescription())).orElse(new Prescription());
//        Staff receptionist = staffRepository.findById(Long.valueOf(request.getIdReceptionist())).orElse(new Staff());
//        EyeCategory eyeCategory = eyeCategoryRepository.findById(Long.valueOf(request.getIdBooking())).orElse(new EyeCategory());
//
//        // Lấy danh sách thuốc dựa trên idList trong request
//        List<Medicine> medicines = new ArrayList<>();
//        for (String medicineId : request.getIdList()) {
//            Medicine medicine = medicineRepository.findById(Long.valueOf(medicineId)).orElse(null);
//            if (medicine != null) {
//                medicines.add(medicine);
//            }
//        }
//
//        // Tính tổng giá trị từ danh sách thuốc và eyeCategory
//        BigDecimal totalPrice = calculateTotal(medicines, eyeCategory);
//
//        bill.setPrescription(prescription);
//        bill.setReceptionist(receptionist);
//        bill.setDateDisease(LocalDateTime.now());
//        bill.setTotalPrice(totalPrice);
//        billRepository.save(bill);
//    }
//
//    private BigDecimal calculateTotal(List<Medicine> medicines, EyeCategory eyeCategory) {
//        BigDecimal total = BigDecimal.ZERO;
//
//        for (Medicine medicine : medicines) {
//            total = total.add(medicine.getPriceMedicine());
//        }
//
//        total = total.add(eyeCategory.getPrice());
//
//        return total;
//    }


    public BillResponse findShowDetailById(Long id) {
        var bill = billRepository.findById(id).orElse(new Bill());
        var result = AppUtils.mapper.map(bill, BillResponse.class);

        result.setDateDisease(bill.getDateDisease());
        Staff receptionist = bill.getReceptionist();
        result.setReceptionist(new OptionResponse(receptionist.getId().toString(), receptionist.getUser().getFullName()));
        result.setTotalPrice(bill.getTotalPrice());

        if (bill.getPrescription() != null && bill.getPrescription().getMedicinePrescriptions() != null) {
            result.setMedicinesList(bill.getPrescription().getMedicinePrescriptions()
                    .stream()
                    .map(item -> {
                        MedicineResponse medicineResponse = new MedicineResponse();
                        medicineResponse.setId(item.getMedicine().getId());
                        medicineResponse.setNameMedicine(item.getMedicine().getNameMedicine());
                        medicineResponse.setPriceMedicine(item.getPrice());
                        medicineResponse.setQuantity(item.getQuantity());
                        if (item.getType() != null) {
                            medicineResponse.setType(item.getType().toString());
                        }
                        return medicineResponse;
                    })
                    .collect(Collectors.toList()));
        }

        return result;
    }

}
