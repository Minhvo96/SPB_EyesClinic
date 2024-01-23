package com.codegym.spb_eyesclinic_project.service.billService;

import com.codegym.spb_eyesclinic_project.domain.*;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.Bill;
import com.codegym.spb_eyesclinic_project.domain.Staff;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillDateRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillTotalResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.medicineDTO.MedicineResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.response.OptionResponse;
import com.codegym.spb_eyesclinic_project.repository.*;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    private final EyeCategoryRepository eyeCategoryRepository;

    private final MedicineRepository medicineRepository;


   public void create(BillRequest request) {
        Bill bill = AppUtils.mapper.map(request, Bill.class);
        Prescription prescription = prescriptionRepository.findById(Long.valueOf(request.getIdPrescription())).orElse(new Prescription());
        Staff receptionist = staffRepository.findById(Long.valueOf(request.getIdReceptionist())).orElse(new Staff());
        bill.setPrescription(prescription);
        bill.setReceptionist(receptionist);
        bill.setDateDisease(LocalDateTime.now());
        bill.getPrescription().getBooking().setStatus(EStatus.COMPLETED);
        billRepository.save(bill);

    }

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
                        "",
                        item.getNoteMedicine(),
                        item.getUsingMedicine()))
                .collect(Collectors.toList()));
        return billResponse;
    }

    public List<BillTotalResponse> getBillsByMonthYear(BillDateRequest billDateRequest) {
        List<Bill> bills = billRepository.findAllByMonthYear(Integer.valueOf(billDateRequest.getYear()), Integer.valueOf(billDateRequest.getMonth()));

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
