package com.codegym.spb_eyesclinic_project.service.user.request;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserSaveRequest {


    private String fullName;

    private String phoneNumber;

    private String role;

    private String address;

    private String password;

    private String age;

    private String avatar;

    private String experience;

    private String degree;

    private String status;
}
