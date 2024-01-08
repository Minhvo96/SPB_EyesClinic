package com.codegym.spb_eyesclinic_project.service.bookingService;
import com.codegym.spb_eyesclinic_project.domain.Booking;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingRequest;
import com.codegym.spb_eyesclinic_project.repository.BookingRepository;
import com.codegym.spb_eyesclinic_project.repository.CustomerRepository;
import com.codegym.spb_eyesclinic_project.repository.EyeCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final EyeCategoryRepository eyeCategoryRepository;

    private final CustomerRepository customerRepository;

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }
    public Booking getByStatus(EStatus string) {
        return bookingRepository.findBookingByStatus(string);
    }

    public List<Booking> getByStatusPending(EStatus string, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateBooking = LocalDate.parse(date, formatter);
        return bookingRepository.findBookingListByStatus(string, dateBooking);
    }
    public Optional<Booking> getById( Long id) {
        return bookingRepository.findById(id);
    }


    public void create(BookingRequest request) {
        Booking booking = new Booking();
        var eyeCategory = eyeCategoryRepository.findById(Long.valueOf(request.getIdEyeCategory()));
        var customer = customerRepository.findById(Long.valueOf(request.getIdCustomer()));
        booking.setEyeCategory(eyeCategory.get());
        booking.setCustomer(customer.get());
        booking.setStatus(EStatus.PENDING);

//        booking.setDateBooking(LocalDate.parse(request.getDateBooking()));
        booking.setTimeBooking(request.getTimeBooking());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(request.getDateBooking());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            booking.setDateBooking(sqlDate.toLocalDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bookingRepository.save(booking);
    }

    public Booking update(BookingRequest request, Long id) {
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

        if(request.getStatus().toUpperCase().equals("UNPAID")){
            result.setStatus(EStatus.UNPAID);
        }

        if(request.getStatus().toUpperCase().equals("EXAMINING")){
            result.setStatus(EStatus.EXAMINING);
        }

        if(request.getStatus().toUpperCase().equals("PENDING")){
            result.setStatus(EStatus.PENDING);
        }


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(request.getDateBooking());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            result.setDateBooking(sqlDate.toLocalDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        result.setTimeBooking(request.getTimeBooking());
        bookingRepository.save(result);

        return result;
    }



}
