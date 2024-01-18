package com.codegym.spb_eyesclinic_project.repository;

import com.codegym.spb_eyesclinic_project.domain.Bill;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query ("select bill from Bill bill WHERE YEAR(bill.dateDisease) = :year AND MONTH(bill.dateDisease) = :month ")
    List<Bill> findAllByMonthYear(@Param("year") Integer year, @Param("month") Integer month);

    @Query ("select bill from Bill bill WHERE YEAR(bill.dateDisease) = :year")
    List<Bill> findAllByYear(@Param("year") Integer year);
}
