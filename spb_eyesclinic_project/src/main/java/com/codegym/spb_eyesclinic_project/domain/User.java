package com.codegym.spb_eyesclinic_project.domain;

import com.codegym.spb_eyesclinic_project.domain.Enum.EDegree;
import com.codegym.spb_eyesclinic_project.domain.Enum.ERole;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String phoneNumber;

    private String password;

    @OneToOne(mappedBy = "user")
    private Staff staff;

    @OneToOne(mappedBy = "user")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private ERole role;
}
