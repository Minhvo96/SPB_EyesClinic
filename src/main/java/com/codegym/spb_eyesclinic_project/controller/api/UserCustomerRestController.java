package com.codegym.spb_eyesclinic_project.controller.api;


import com.codegym.spb_eyesclinic_project.domain.Customer;
import com.codegym.spb_eyesclinic_project.domain.User;
import com.codegym.spb_eyesclinic_project.service.customer.CustomerService;
import com.codegym.spb_eyesclinic_project.service.user.UserService;
import com.codegym.spb_eyesclinic_project.service.user.request.UserSaveRequest;
//import com.codegym.spb_eyesclinic_project.service.user.UserService;
import com.codegym.spb_eyesclinic_project.service.user.response.UserListResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserCustomerRestController {

    @Autowired
    private UserService userService;

    private CustomerService customerService;


    @GetMapping("")
    public ResponseEntity<?> getListUser() {
        List<UserListResponse> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public String createUser(@RequestBody UserSaveRequest request) {
        return userService.createUser(request);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser() {
        return null;
    }
}
