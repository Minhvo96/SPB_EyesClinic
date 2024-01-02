package com.codegym.spb_eyesclinic_project.repository;

import com.codegym.spb_eyesclinic_project.domain.MedicinePrescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicinePrescriptionRepository extends JpaRepository<MedicinePrescription, Long> {
}
