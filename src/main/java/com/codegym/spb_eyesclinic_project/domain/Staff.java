package com.codegym.spb_eyesclinic_project.domain;

import com.codegym.spb_eyesclinic_project.domain.Enum.EDegree;
import com.codegym.spb_eyesclinic_project.domain.Enum.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "staffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String address;

    private String experience;

    @Enumerated(EnumType.STRING)
    private EDegree degree;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @OneToOne
    private Avatar avatar;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Staff(Long id) {
        this.id = id;
    }
}
