package com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO;


import com.codegym.spb_eyesclinic_project.domain.dto.request.OptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class PrescriptionShowDetailResponse {

    private Long id;

    private Long idBooking;

    private Long idDoctor;

    private String eyeSight;

    private String diagnose;

    private String note;

    private BigDecimal totalAmount;



    private List<String> idsMedicine;
}
