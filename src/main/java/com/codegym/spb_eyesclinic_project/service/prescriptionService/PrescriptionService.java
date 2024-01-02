package com.codegym.spb_eyesclinic_project.service.prescriptionService;

import com.codegym.spb_eyesclinic_project.domain.MedicinePrescription;
import com.codegym.spb_eyesclinic_project.domain.Prescription;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.request.OptionRequest;
import com.codegym.spb_eyesclinic_project.repository.*;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void create(PrescriptionRequest request) {
        var result = AppUtils.mapper.map(request, Prescription.class);
        AppUtils.mapper.map(request, result);
        var doctor = staffRepository.findById(Long.valueOf(request.getIdDoctor()));
        var booking = bookingRepository.findById(Long.valueOf(request.getIdBooking()));

        result.setDoctor(doctor.get());
        result.setBooking(booking.get());
        prescriptionRepository.save(result);

        var medicines = medicineRepository.findAllById(request.getIdsMedicine()
                .stream()
                .map(medicine -> Long.valueOf(medicine.getId()))
                .collect(Collectors.toList()));

        List<MedicinePrescription> medicinePrescriptions = new ArrayList<>();
        for (int i = 0; i < medicines.size(); i++) {
            MedicinePrescription medicinePrescription = new MedicinePrescription(result, medicines.get(i));
            medicinePrescriptions.add(medicinePrescription);
        }
        medicinePrescriptionRepository.saveAll(medicinePrescriptions);
    }

    public void update(PrescriptionRequest request, Long id){
        var result = prescriptionRepository.findById(id).get();
        AppUtils.mapper.map(request,result);
        prescriptionRepository.save(result);

        medicinePrescriptionRepository.deleteAllById(result.getMedicinePrescriptions()
                .stream()
                .map(item -> item.getId())
                .collect(Collectors.toList()));

        var medicines = medicineRepository.findAllById(request.getIdsMedicine()
                .stream()
                .map(medicine -> Long.valueOf(medicine.getId()))
                .collect(Collectors.toList()));

        List<MedicinePrescription> medicinePrescriptions = new ArrayList<>();
        for (int i = 0; i < medicines.size(); i++) {
            MedicinePrescription medicinePrescription = new MedicinePrescription(result, medicines.get(i));
            medicinePrescriptions.add(medicinePrescription);
        }
        medicinePrescriptionRepository.saveAll(medicinePrescriptions);
    }
}

