package com.codegym.spb_eyesclinic_project.service.customer.request;



import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerSaveRequest {
    private String age;
    private String phoneNumber;
    private String address;
    private String role;

}
