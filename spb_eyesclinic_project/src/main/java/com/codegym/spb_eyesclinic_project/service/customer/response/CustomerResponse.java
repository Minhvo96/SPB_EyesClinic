package com.codegym.spb_eyesclinic_project.service.customer.response;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class CustomerResponse {
    private Long id;

    private String age;

    private String phoneNumber;

    private String address;

    private String role;
}
