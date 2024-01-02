package com.codegym.spb_eyesclinic_project.service.staff.response;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class StaffResponse {
    private Long id;

    private String phoneNumber;

    private String address;

    private String experience;

    private String role;
}
