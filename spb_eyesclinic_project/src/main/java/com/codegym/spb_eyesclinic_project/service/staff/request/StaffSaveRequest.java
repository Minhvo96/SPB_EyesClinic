package com.codegym.spb_eyesclinic_project.service.staff.request;

import com.codegym.spb_eyesclinic_project.domain.Enum.EDegree;
import com.codegym.spb_eyesclinic_project.domain.Enum.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@Data
public class StaffSaveRequest {

    private String phoneNumber;

    private String address;

    private String experience;

    private String degree;

    private String role;
}
