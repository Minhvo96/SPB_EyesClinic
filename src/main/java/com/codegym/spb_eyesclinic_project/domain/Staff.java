package com.codegym.spb_eyesclinic_project.domain;
import com.codegym.spb_eyesclinic_project.domain.Enum.EDegree;
import com.codegym.spb_eyesclinic_project.domain.Enum.ERole;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
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

    private String experience;

    @Enumerated(EnumType.STRING)
    private EDegree degree;

    @OneToOne
    private Avatar avatar;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    public Staff(Long id) {
        this.id = id;
    }

    public Staff(String experience, EDegree degree, Avatar avatar, User user) {
        this.experience = experience;
        this.degree = degree;
        this.avatar = avatar;
        this.user = user;
    }
}
