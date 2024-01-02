package com.codegym.spb_eyesclinic_project.controller.api;


import com.codegym.spb_eyesclinic_project.service.customer.CustomerService;
import com.codegym.spb_eyesclinic_project.service.customer.response.CustomerResponse;
import com.codegym.spb_eyesclinic_project.service.staff.StaffService;
import com.codegym.spb_eyesclinic_project.service.staff.response.StaffResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserStaffRestController {

//    private List<User> users = new ArrayList<User>();
//
//    @PostMapping("/user")
//    public User create (@RequestBody User user) {
//        users.add(user);
//        return user;
//    }
//
//    @GetMapping("/users")
//    public List<User> getAll (){
//        return users;
//    }

//    private final StaffService staffService;
//    @GetMapping("/{id}")
//    public ResponseEntity<StaffResponse> getCurrentEye(@PathVariable String id) {
//        return new ResponseEntity<>(staffService.getStaffResponseByID(Long.valueOf(id)), HttpStatus.OK);
//
//    }

}
