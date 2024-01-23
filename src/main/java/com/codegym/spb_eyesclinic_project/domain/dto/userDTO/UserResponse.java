package com.codegym.spb_eyesclinic_project.domain.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;

    private String fullName;

    private String phoneNumber;

    private String roles;

    private String image;
    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"fullName\":\"" + fullName + '\"' +
                ", \"phoneNumber\":\"" + phoneNumber + '\"' +
                ", \"roles\":\"" + roles + '\"' +
                ", \"image\":\"" + image + '\"' +
                '}';
    }
}
