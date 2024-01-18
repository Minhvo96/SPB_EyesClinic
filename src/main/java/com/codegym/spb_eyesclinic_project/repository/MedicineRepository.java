package com.codegym.spb_eyesclinic_project.repository;

import com.codegym.spb_eyesclinic_project.domain.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

//    @Query("SELECT m.id, m.priceMedicine FROM Medicine m WHERE m.id IN :ids")
//    List<Object[]> findPricesByIds(@Param("ids") List<Long> medicineIds);
}
