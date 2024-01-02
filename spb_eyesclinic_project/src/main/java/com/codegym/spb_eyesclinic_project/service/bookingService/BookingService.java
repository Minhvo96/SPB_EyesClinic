package com.codegym.spb_eyesclinic_project.service.bookingService;

import com.codegym.spb_eyesclinic_project.domain.Booking;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.EyeCategory;
import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingRequest;
import com.codegym.spb_eyesclinic_project.repository.BookingRepository;
import com.codegym.spb_eyesclinic_project.repository.CustomerRepository;
import com.codegym.spb_eyesclinic_project.repository.EyeCategoryRepository;
import com.codegym.spb_eyesclinic_project.repository.UserRepository;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final EyeCategoryRepository eyeCategoryRepository;

    private final CustomerRepository customerRepository;
    public void create(BookingRequest request) {
        Booking booking = new Booking();
        var eyeCategory = eyeCategoryRepository.findById(Long.valueOf(request.getIdEyeCategory()));
        var customer = customerRepository.findById(Long.valueOf(request.getIdCustomer()));
        booking.setEyeCategory(eyeCategory.get());
        booking.setCustomer(customer.get());
        booking.setStatus(EStatus.PENDING);
        booking.setDateBooking(LocalDateTime.now());
        // Định dạng của chuỗi ngày giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        // Chuyển đổi chuỗi thành LocalDateTime
        LocalDateTime dateAppointment = LocalDateTime.parse(request.getDateAppointment(), formatter);
        booking.setDateAppointment(dateAppointment);
        bookingRepository.save(booking);
    }

    public void update(BookingRequest request, Long id) {
       var result = bookingRepository.findById(id).get();
       var eyeCategory = eyeCategoryRepository.findById(Long.valueOf(request.getIdEyeCategory()));
       var customer = customerRepository.findById(Long.valueOf(request.getIdCustomer()));
       if(eyeCategory.get().getId().toString().equals(request.getIdEyeCategory())){
           result.setEyeCategory(eyeCategory.get());
       }
       if(customer.get().getId().toString().equals(request.getIdCustomer())){
           result.setCustomer(customer.get());
       }
       if(request.getStatus().toUpperCase().equals("CANCELLED")){
           result.setStatus(EStatus.CANCELLED);
       }
       if(request.getStatus().toUpperCase().equals("WAITING")){
            result.setStatus(EStatus.WAITING);
       }
       if(request.getStatus().toUpperCase().equals("COMPLETED")){
            result.setStatus(EStatus.COMPLETED);
       }
        // Định dạng của chuỗi ngày giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        // Chuyển đổi chuỗi thành LocalDateTime
        LocalDateTime dateAppointment = LocalDateTime.parse(request.getDateAppointment(), formatter);
        result.setDateAppointment(dateAppointment);
        bookingRepository.save(result);
    }
}
