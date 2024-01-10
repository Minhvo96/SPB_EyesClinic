package com.codegym.spb_eyesclinic_project.domain.dto.authDTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class RegisterRequest {

    private String fullName;

    private String phoneNumber;

    private String password;

    private String email;

    private String address;

}
