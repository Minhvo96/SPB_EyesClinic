package com.codegym.spb_eyesclinic_project.repository;


import com.codegym.spb_eyesclinic_project.domain.Booking;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.Staff;
import com.codegym.spb_eyesclinic_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    @Query("SELECT staff FROM Staff staff WHERE staff.status = :search")
    List<Staff> findStaffByStatus(@Param("search") EStatus search);
    @Query("SELECT staff FROM Staff staff WHERE staff.user.id = :id")
    Staff findStaffByUser(Long id);
}
