package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.service.user.UserServices;
import com.codegym.spb_eyesclinic_project.service.user.request.UserSaveRequest;
import com.codegym.spb_eyesclinic_project.service.user.response.UserListResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserRestController {

    @Autowired
    private UserServices userService;

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
