package com.codegym.spb_eyesclinic_project.service.customer;

import com.codegym.spb_eyesclinic_project.domain.Booking;
import com.codegym.spb_eyesclinic_project.domain.Customer;
import com.codegym.spb_eyesclinic_project.domain.Enum.ERole;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.User;
import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingShowDetailResponse;
import com.codegym.spb_eyesclinic_project.repository.BookingRepository;
import com.codegym.spb_eyesclinic_project.repository.CustomerRepository;

import com.codegym.spb_eyesclinic_project.service.customer.request.CustomerSaveRequest;
import com.codegym.spb_eyesclinic_project.service.customer.response.CustomerResponse;
import com.codegym.spb_eyesclinic_project.service.response.SelectOptionResponse;
import com.codegym.spb_eyesclinic_project.service.user.request.UserSaveRequest;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
    }

    public CustomerResponse getCustomerResponseByID(Long id) {
        return customerRepository.findById(id)
                .map(customer -> AppUtils.mapper
                        .map(customer, CustomerResponse.class))
                .orElseThrow(()-> new RuntimeException("Not found"));
    }

    public List<CustomerResponse> getAll() {

        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponse> customerResponses =  customers.stream()
                .map(customer -> {
                    CustomerResponse customerResponse = new CustomerResponse();
                    customerResponse.setId(customer.getId());
                    customerResponse.setFullName(customer.getUser().getFullName());
                    customerResponse.setPhoneNumber(customer.getUser().getPhoneNumber());
                    customerResponse.setAge(customer.getAge());
                    customerResponse.setAddress(customer.getUser().getAddress());

                    return customerResponse;
                })
                .collect(Collectors.toList());

        return customerResponses;
    }

    public void saveCustomer(CustomerSaveRequest request) {
        var customer = AppUtils.mapper.map(request, Customer.class);
//        var customer = new Customer();
//        customer.setUserId(request.getUserId());
//        customer.setPhoneNumber(request.getPhoneNumber());
        customerRepository.save(customer);
    }

    public ResponseEntity<Integer> getCompletedBookingsCountByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        EStatus completedStatus = EStatus.COMPLETED;

        int completedCount = bookingRepository.findBookingsCountByCustomerAndStatus(customer, completedStatus);

        return ResponseEntity.ok(completedCount);
    }

    public ResponseEntity<Integer> getCancelledBookingsCountByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        EStatus cancelledStatus = EStatus.CANCELLED;

        int cancelledCount = bookingRepository.findBookingsCountByCustomerAndStatus(customer, cancelledStatus);

        return ResponseEntity.ok(cancelledCount);
    }
}
