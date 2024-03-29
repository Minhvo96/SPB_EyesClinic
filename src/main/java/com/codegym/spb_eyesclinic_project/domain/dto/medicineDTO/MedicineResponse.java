package com.codegym.spb_eyesclinic_project.domain.dto.medicineDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicineResponse {
    private Long id;

    private String nameMedicine;

    private BigDecimal priceMedicine;

    private Long quantity;

    private String type;

    private String noteMedicine;

    private String useMedicine;

    public MedicineResponse(Long id, String nameMedicine, BigDecimal priceMedicine, Long quantity, String type) {
        this.id = id;
        this.nameMedicine = nameMedicine;
        this.priceMedicine = priceMedicine;
        this.quantity = quantity;
        this.type = type;
    }
}
