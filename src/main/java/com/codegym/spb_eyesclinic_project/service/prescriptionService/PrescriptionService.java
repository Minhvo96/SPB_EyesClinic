package com.codegym.spb_eyesclinic_project.service.prescriptionService;

import com.codegym.spb_eyesclinic_project.domain.*;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingShowDetailResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.medicineDTO.MedicineResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionEyeResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionShowDetailResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.request.OptionRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.response.OptionResponse;
import com.codegym.spb_eyesclinic_project.repository.*;
import com.codegym.spb_eyesclinic_project.service.bookingService.BookingService;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                        request.getIdsMedicine().get(i).getUsingMedicine(),
                        request.getIdsMedicine().get(i).getNoteMedicine());
                medicinePrescriptions.add(medicinePrescription);
            }
            medicinePrescriptionRepository.saveAll(medicinePrescriptions);
        }
    }

    public PrescriptionEyeResponse getEyesInPrescriptionByBookingId (Long id) {
        var prescription = prescriptionRepository.getPrescriptionByIdBooking(id);
        if(prescription == null) {
            return new PrescriptionEyeResponse();
        }
        var result = AppUtils.mapper.map(prescription, PrescriptionEyeResponse.class);
        result.setEyeSight(prescription.getEyeSight());
        return result;
    }

    public PrescriptionShowDetailResponse findShowDetailById(Long id) {
        var prescription = prescriptionRepository.findById(id).orElse(new Prescription());
        var result = AppUtils.mapper.map(prescription, PrescriptionShowDetailResponse.class);
        result.setIdBooking(prescription.getBooking().getId());
        result.setDoctor(new OptionResponse(prescription.getDoctor().getId().toString(), prescription.getDoctor().getUser().getFullName()));
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

        result.setMedicines(prescription
                .getMedicinePrescriptions()
                .stream().map(medicinePrescription -> new MedicineResponse(
                        medicinePrescription.getId(),
                        medicinePrescription.getMedicine().getNameMedicine(),
                        medicinePrescription.getPrice(),
                        medicinePrescription.getQuantity(),
                        medicinePrescription.getMedicine().getType().toString(),
                        medicinePrescription.getNoteMedicine(),
                        medicinePrescription.getUsingMedicine()
                ))
                .collect(Collectors.toList()));

        return result;
    }

    public String findByIdBooking(Long id) {
        return prescriptionRepository.findPrescriptionByBookingId(id).getId().toString();
    }



}

