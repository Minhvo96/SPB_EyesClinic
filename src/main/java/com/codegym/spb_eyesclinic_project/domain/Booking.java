package com.codegym.spb_eyesclinic_project.domain;

import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private EyeCategory eyeCategory;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateBooking;

    private String timeBooking;

    @ManyToOne
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private EStatus status;
}
