package com.codegym.spb_eyesclinic_project.service.prescriptionService;

import com.codegym.spb_eyesclinic_project.domain.*;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingShowDetailResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.medicineDTO.MedicineResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionShowDetailResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.request.OptionRequest;
import com.codegym.spb_eyesclinic_project.repository.*;
import com.codegym.spb_eyesclinic_project.service.bookingService.BookingService;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    private final StaffRepository staffRepository;

    private final BookingRepository bookingRepository;

    private final MedicinePrescriptionRepository medicinePrescriptionRepository;

    private final MedicineRepository medicineRepository;

    private final BookingService bookingService;

    public Page<PrescriptionResponse> getAll(Pageable pageable, String search) {
        search = "%" + search + "%";
        return prescriptionRepository.searchEverything(search, pageable).map(e -> {
            var result = new PrescriptionResponse();

            result.setId(e.getId());
            result.setIdBooking(e.getBooking().getId());
            result.setIdDoctor(e.getDoctor().getId());
            result.setEyeSight(e.getEyeSight());
            result.setDiagnose(e.getDiagnose());
            result.setNote(e.getNote());
            result.setStatus(e.getBooking().getStatus().toString());

            result.setIdsMedicine(e.getMedicinePrescriptions()
                    .stream().map(m -> m.getMedicine().getNameMedicine())
                    .collect(Collectors.toList()));
            return result;
        });
    }



    public void create(PrescriptionRequest request) {
        var result = AppUtils.mapper.map(request, Prescription.class);
        var doctor = staffRepository.findById(Long.valueOf(request.getIdDoctor()));
        var booking = bookingRepository.findById(Long.valueOf(request.getIdBooking()));

        result.setDoctor(doctor.get());
        result.setBooking(booking.get());
        prescriptionRepository.save(result);

        var idsMedicine = request.getIdsMedicine().stream().map(item -> Long.valueOf(item.getId())).collect(Collectors.toList());

        var medicines = medicineRepository.findAllById(idsMedicine);

        List<MedicinePrescription> medicinePrescriptions = new ArrayList<>();

        for (int i = 0; i < medicines.size(); i++) {
            MedicinePrescription medicinePrescription = new MedicinePrescription(
                    result,
                    medicines.get(i),
                    Long.valueOf(request.getIdsMedicine().get(i).getQuantity()),
                    medicines.get(i).getPriceMedicine(),
                    request.getIdsMedicine().get(i).getUsingMedicine());

            medicinePrescriptions.add(medicinePrescription);
        }
        medicinePrescriptionRepository.saveAll(medicinePrescriptions);
    }

    public void update(PrescriptionRequest request, Long id){
//        var result = prescriptionRepository.findById(id).get();
//        AppUtils.mapper.map(request,result);
//        prescriptionRepository.save(result);
//
//        medicinePrescriptionRepository.deleteAllById(result.getMedicinePrescriptions()
//                .stream()
//                .map(item -> item.getId())
//                .collect(Collectors.toList()));
//
//        var medicines = medicineRepository.findAllById(request.getIdsMedicine()
//                .stream()
//                .map(medicine -> Long.valueOf(medicine.getId()))
//                .collect(Collectors.toList()));
//
//        List<MedicinePrescription> medicinePrescriptions = new ArrayList<>();
//        for (int i = 0; i < medicines.size(); i++) {
//            MedicinePrescription medicinePrescription = new MedicinePrescription(result, medicines.get(i));
//            medicinePrescriptions.add(medicinePrescription);
//        }
//        medicinePrescriptionRepository.saveAll(medicinePrescriptions);
    }

    public PrescriptionShowDetailResponse findShowDetailById(Long id) {
        var prescription = prescriptionRepository.findById(id).orElse(new Prescription());
        var result = AppUtils.mapper.map(prescription, PrescriptionShowDetailResponse.class);

        result.setIdBooking(prescription.getBooking().getId());
        result.setIdDoctor(prescription.getDoctor().getId());
        result.setEyeSight(prescription.getEyeSight());
        result.setDiagnose(prescription.getDiagnose());
        result.setNote(prescription.getNote());

        // Tính total amount
        List<MedicinePrescription> medicinePrescriptions = prescription.getMedicinePrescriptions();
        EyeCategory eyeCategory = prescription.getBooking().getEyeCategory();
        BigDecimal serviceEyeCate = eyeCategory.getPrice();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (MedicinePrescription medicinePrescription : medicinePrescriptions) {
            BigDecimal price = medicinePrescription.getPrice().multiply(BigDecimal.valueOf(medicinePrescription.getQuantity()));
            totalAmount = totalAmount.add(price);
        }

        result.setTotalAmount(serviceEyeCate.add(totalAmount));

        result.setIdsMedicine(prescription
                .getMedicinePrescriptions()
                .stream().map(medicinePrescription -> medicinePrescription.getMedicine().getNameMedicine() + "," + medicinePrescription.getQuantity() + "," + medicinePrescription.getMedicine().getPriceMedicine() + "," + medicinePrescription.getMedicine().getType())
                .collect(Collectors.toList()));

        return result;
    }

    public String findByIdBooking(Long id) {
        return prescriptionRepository.findPrescriptionByBookingId(id).getId().toString();
    }
}

