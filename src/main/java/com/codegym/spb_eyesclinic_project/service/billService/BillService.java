package com.codegym.spb_eyesclinic_project.service.billService;

import com.codegym.spb_eyesclinic_project.domain.Bill;
import com.codegym.spb_eyesclinic_project.domain.Staff;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillDateRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillTotalResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.medicineDTO.MedicineResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.response.OptionResponse;
import com.codegym.spb_eyesclinic_project.repository.BillRepository;
import com.codegym.spb_eyesclinic_project.repository.PrescriptionRepository;
import com.codegym.spb_eyesclinic_project.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
//                        item.getType().toString()
                        ""))
                .collect(Collectors.toList()));
        return billResponse;
    }

    public List<BillTotalResponse> getBillsByMonthYear(BillDateRequest billDateRequest) {
        List<Bill> bills = billRepository.findAllByMonthYear(Integer.valueOf(billDateRequest.getYear()) , Integer.valueOf(billDateRequest.getMonth()));

        List<BillTotalResponse> billTotalResponses = bills.stream().map(item -> new BillTotalResponse(
                        String.valueOf(item.getDateDisease().toLocalDate().getDayOfMonth()),
                        item.getTotalPrice()))
        .collect(Collectors.toList());

        List<BillTotalResponse> outputList = new ArrayList<>();

        for (BillTotalResponse item : billTotalResponses) {
            String date = item.getDate();
            BigDecimal total = item.getTotal();

            boolean found = false;
            for (BillTotalResponse outputItem : outputList) {
                if (outputItem.getDate().equals(date)) {
                    BigDecimal currentTotal = outputItem.getTotal();
                    outputItem.setTotal(currentTotal.add(total));
                    found = true;
                    break;
                }
            }

            if (!found) {
                var billTotalResponse = new BillTotalResponse();
                billTotalResponse.setDate(date);
                billTotalResponse.setTotal(total);
                outputList.add(billTotalResponse);
            }
        }

        return outputList;
    }

    public BigDecimal getBillsByYear(BillDateRequest billDateRequest) {
        List<Bill> bills = billRepository.findAllByYear(Integer.valueOf(billDateRequest.getYear()));
        BigDecimal total = new BigDecimal(0L);

        for(Bill bill: bills) {
            total = total.add(bill.getTotalPrice());
        }

        return total;
    }
}
