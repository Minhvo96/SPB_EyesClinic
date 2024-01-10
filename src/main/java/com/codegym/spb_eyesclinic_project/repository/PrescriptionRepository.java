package com.codegym.spb_eyesclinic_project.repository;

import com.codegym.spb_eyesclinic_project.domain.Booking;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("SELECT prescription FROM Prescription prescription WHERE prescription.booking.id = :id")
    Prescription getPrescriptionByIdBooking(@Param("id") Long id);
}
