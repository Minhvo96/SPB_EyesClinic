package com.codegym.spb_eyesclinic_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateDisease;

    private BigDecimal totalPrice;

    @OneToOne
    private Prescription prescription;

    @ManyToOne
    private Staff receptionist;


    public Bill(LocalDateTime dateDisease, Prescription prescription, Staff receptionist, BigDecimal totalPrice) {

        this.dateDisease = dateDisease;
        this.prescription = prescription;
        this.receptionist = receptionist;
        this.totalPrice = totalPrice;
    }
}
