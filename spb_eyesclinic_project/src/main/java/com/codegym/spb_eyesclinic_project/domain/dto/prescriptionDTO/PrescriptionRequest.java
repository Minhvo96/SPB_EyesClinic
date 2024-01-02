package com.codegym.spb_eyesclinic_project.domain.dto.prescriptionDTO;

import com.codegym.spb_eyesclinic_project.domain.dto.request.OptionRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrescriptionRequest {

    private String idBooking;

    private String idDoctor;

    private String eyeSight;

    private String diagnose;

    private String note;

    private List<OptionRequest> idsMedicine;

}
