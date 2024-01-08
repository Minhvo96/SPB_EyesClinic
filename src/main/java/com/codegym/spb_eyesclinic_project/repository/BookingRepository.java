package com.codegym.spb_eyesclinic_project.repository;

import com.codegym.spb_eyesclinic_project.domain.Booking;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT book FROM Booking book WHERE book.status = :search")
    Booking findBookingByStatus(@Param("search") EStatus search);

    @Query("SELECT book FROM Booking book WHERE book.status = :search and book.dateBooking = :date")
    List<Booking> findBookingListByStatus(@Param("search") EStatus search, @Param("date") LocalDate date);


}
