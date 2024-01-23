package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.domain.Booking;
import com.codegym.spb_eyesclinic_project.domain.User;
import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingStatsResponse;
import com.codegym.spb_eyesclinic_project.repository.CustomerRepository;
import com.codegym.spb_eyesclinic_project.repository.MedicineRepository;
import com.codegym.spb_eyesclinic_project.service.customer.CustomerService;
import com.codegym.spb_eyesclinic_project.service.customer.request.CustomerSaveRequest;
import com.codegym.spb_eyesclinic_project.service.customer.response.CustomerResponse;
import com.codegym.spb_eyesclinic_project.service.response.SelectOptionResponse;
import com.codegym.spb_eyesclinic_project.service.user.request.UserSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
public class CustomerRestController {

    private CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public void createCustomer(CustomerSaveRequest request) {
        customerService.saveCustomer(request);
    }

    @GetMapping("/{customerId}/booking-stats")
    public ResponseEntity<BookingStatsResponse> getCustomerBookingStats(@PathVariable Long customerId) {
        ResponseEntity<Integer> completedCountResponse = customerService.getCompletedBookingsCountByCustomerId(customerId);
        ResponseEntity<Integer> cancelledCountResponse = customerService.getCancelledBookingsCountByCustomerId(customerId);

        if (completedCountResponse.getStatusCode().is2xxSuccessful() && cancelledCountResponse.getStatusCode().is2xxSuccessful()) {
            Integer completedCount = completedCountResponse.getBody();
            Integer cancelledCount = cancelledCountResponse.getBody();

            BookingStatsResponse response = new BookingStatsResponse(completedCount, cancelledCount);

            return ResponseEntity.ok(response);
        } else {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/search/{keyword}")
    public List<CustomerResponse> searchCustomer(@PathVariable String keyword) {
        return customerService.searchPatient(keyword);
    }
}
