package com.codegym.spb_eyesclinic_project.domain.dto.billDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillRequest {
    private String idPrescription;

    private String idReceptionist;

    private String idBooking;

    private String totalPrice;


//    private List<String> idList;

}
