package com.codegym.spb_eyesclinic_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String age;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Customer(Long id) {
        this.id = id;
    }

    public Customer(String age, User user) {
        this.age = age;
        this.user = user;
    }
}
