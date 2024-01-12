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
import java.util.Optional;
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
        var doctor = staffRepository.findById(Long.valueOf(request.getIdDoctor()));
        var booking = bookingRepository.findById(Long.valueOf(request.getIdBooking()));

        Optional<Prescription> existingPrescriptionOptional = Optional.ofNullable(prescriptionRepository.getPrescriptionByIdBooking(Long.valueOf(request.getIdBooking())));

        if (existingPrescriptionOptional.isPresent()) {
            result.setEyeSight(request.getEyeSight());
            result.setId(existingPrescriptionOptional.get().getId());
            prescriptionRepository.save(result);
        } else {
            result.setDoctor(doctor.get());
            result.setBooking(booking.get());
            prescriptionRepository.save(result);
        }
    }

    public void updatePrescription(PrescriptionRequest request, Long id){
        Optional<Prescription> existingPrescriptionOptional = Optional.ofNullable(prescriptionRepository.getPrescriptionByIdBooking(id));

        if (existingPrescriptionOptional.isPresent()) {
            Prescription existingPrescription = existingPrescriptionOptional.get();

            AppUtils.mapper.map(request, existingPrescription);

            var doctor = staffRepository.findById(Long.valueOf(request.getIdDoctor()));
            existingPrescription.setDoctor(doctor.get());

            var booking = bookingRepository.findById(id);
            existingPrescription.setBooking(booking.get());
            existingPrescription.setId(existingPrescription.getId());

            prescriptionRepository.save(existingPrescription);


            var idsMedicine = request.getIdsMedicine().stream().map(item -> Long.valueOf(item.getId())).collect(Collectors.toList());
            var medicines = medicineRepository.findAllById(idsMedicine);

            List<MedicinePrescription> medicinePrescriptions = new ArrayList<>();

            for (int i = 0; i < medicines.size(); i++) {
                MedicinePrescription medicinePrescription = new MedicinePrescription(
                        existingPrescription,
                        medicines.get(i),
                        Long.valueOf(request.getIdsMedicine().get(i).getQuantity()),
                        medicines.get(i).getPriceMedicine(),
                        request.getIdsMedicine().get(i).getUsingMedicine());

                medicinePrescriptions.add(medicinePrescription);
            }
            medicinePrescriptionRepository.saveAll(medicinePrescriptions);
        }
    }

    public Prescription getPrescriptionByBookingId (Long id) {
        return prescriptionRepository.getPrescriptionByIdBooking(id);
    }
}

