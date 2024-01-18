package com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PrescriptionResponse {

    private Long id;

    private Long idBooking;

    private Long idDoctor;

    private String eyeSight;

    private String diagnose;

    private String note;

    private String status;

    private List<String> idsMedicine;
}
