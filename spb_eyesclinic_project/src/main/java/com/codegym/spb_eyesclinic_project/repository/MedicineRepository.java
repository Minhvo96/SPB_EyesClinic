package com.codegym.spb_eyesclinic_project.repository;

import com.codegym.spb_eyesclinic_project.domain.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
