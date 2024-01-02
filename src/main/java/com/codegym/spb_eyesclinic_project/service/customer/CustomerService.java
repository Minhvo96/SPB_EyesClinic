package com.codegym.spb_eyesclinic_project.service.customer;

import com.codegym.spb_eyesclinic_project.domain.Customer;
import com.codegym.spb_eyesclinic_project.domain.Enum.ERole;
import com.codegym.spb_eyesclinic_project.domain.User;
import com.codegym.spb_eyesclinic_project.repository.CustomerRepository;

import com.codegym.spb_eyesclinic_project.service.customer.request.CustomerSaveRequest;
import com.codegym.spb_eyesclinic_project.service.customer.response.CustomerResponse;
import com.codegym.spb_eyesclinic_project.service.response.SelectOptionResponse;
import com.codegym.spb_eyesclinic_project.service.user.request.UserSaveRequest;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
    }

//    public Customer findByPhoneNumber(String phoneNumber) {
//        return customerRepository.findCustomerByNumberPhone(phoneNumber).orElseThrow(() -> new RuntimeException("Not Found!"));
//    }

    public CustomerResponse getCustomerResponseByID(Long id) {
        return customerRepository.findById(id)
                .map(customer -> AppUtils.mapper
                        .map(customer, CustomerResponse.class))
                .orElseThrow(()-> new RuntimeException("Not found"));
    }

    public List<SelectOptionResponse> findAll() {
        return customerRepository.findAll().stream()
                .map(customer -> new SelectOptionResponse(customer.getId().toString(), customer.getUser().getFullName()))
                .collect(Collectors.toList());
    }

    public void saveCustomer(CustomerSaveRequest request) {
        var customer = AppUtils.mapper.map(request, Customer.class);
//        var customer = new Customer();
//        customer.setUserId(request.getUserId());
//        customer.setPhoneNumber(request.getPhoneNumber());
        customerRepository.save(customer);
    }

}
