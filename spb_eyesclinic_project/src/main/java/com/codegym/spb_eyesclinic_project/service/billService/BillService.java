package com.codegym.spb_eyesclinic_project.service.billService;

import com.codegym.spb_eyesclinic_project.domain.Bill;
import com.codegym.spb_eyesclinic_project.domain.Staff;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.medicineDTO.MedicineResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.response.OptionResponse;
import com.codegym.spb_eyesclinic_project.repository.BillRepository;
import com.codegym.spb_eyesclinic_project.repository.PrescriptionRepository;
import com.codegym.spb_eyesclinic_project.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BillService {
    private final BillRepository billRepository;

    private final PrescriptionRepository prescriptionRepository;

    private final StaffRepository staffRepository;
    public void create(BillRequest request){
        var prescription = prescriptionRepository.findById(Long.valueOf(request.getIdPrescription())).get();
        var receptionist = staffRepository.findById(Long.valueOf(request.getIdReceptionist())).get();
        Bill bill = new Bill();
        bill.setDateDisease(LocalDateTime.now());
        bill.setPrescription(prescription);
        bill.setReceptionist(receptionist);
        billRepository.save(bill);
    }

    public BillResponse findById(Long id){
        var bill = billRepository.findById(id).get();
        BillResponse billResponse = new BillResponse();
        billResponse.setDateDisease(bill.getDateDisease());
        Staff receptionist = bill.getReceptionist();
        billResponse.setReceptionist( new OptionResponse(receptionist.getId().toString(), receptionist.getUser().getFullName()));

        billResponse.setMedicinesList(bill.getPrescription().getMedicinePrescriptions()
                .stream()
                .map(item -> new MedicineResponse(
                        item.getMedicine().getId(),
                        item.getMedicine().getNameMedicine(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getType().toString()))
                .collect(Collectors.toList()));
        return billResponse;
    }
}
