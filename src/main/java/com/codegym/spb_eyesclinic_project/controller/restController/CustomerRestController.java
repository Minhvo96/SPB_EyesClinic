package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.service.customer.CustomerService;
import com.codegym.spb_eyesclinic_project.service.customer.request.CustomerSaveRequest;
import com.codegym.spb_eyesclinic_project.service.user.request.UserSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
public class CustomerRestController {

    private CustomerService customerService;

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public void createCustomer(CustomerSaveRequest request) {
        customerService.saveCustomer(request);
    }
}
