package com.codegym.spb_eyesclinic_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Booking booking;

    @ManyToOne
    private Staff doctor;

    private String eyeSight;

    private String diagnose;

    private String note;

    @OneToMany(mappedBy = "prescription")
    private List<MedicinePrescription> medicinePrescriptions;
}
