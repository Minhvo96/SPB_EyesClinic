package com.codegym.spb_eyesclinic_project.repository;

import com.codegym.spb_eyesclinic_project.domain.Booking;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.Prescription;
import com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO.PrescriptionShowDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {


    @Query(value = "SELECT p FROM Prescription p " +
            "JOIN p.booking b " +
            "WHERE b.status = 'UNPAID' AND " +
            "(p.diagnose LIKE :search OR " +
            "p.eyeSight LIKE :search OR " +
            "EXISTS (SELECT 1 FROM MedicinePrescription mp WHERE mp.prescription = p AND mp.medicine.nameMedicine LIKE :search))"
    )
    Page<Prescription> searchEverything(String search, Pageable pageable);

    Prescription findPrescriptionByBookingId(Long id);


    @Query("SELECT prescription FROM Prescription prescription WHERE prescription.booking.id = :id")
    Prescription getPrescriptionByIdBooking(@Param("id") Long id);






}