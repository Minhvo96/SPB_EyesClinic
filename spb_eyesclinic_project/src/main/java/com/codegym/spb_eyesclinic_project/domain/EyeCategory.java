package com.codegym.spb_eyesclinic_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "eye_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EyeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameCategory;

    private BigDecimal price;

    private String description;
}
