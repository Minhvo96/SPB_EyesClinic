package com.codegym.spb_eyesclinic_project.service.user.response;


import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserListResponse {
    private Long id;

    private String fullName;

    private String phoneNumber;

    private String role;
}
