package com.codegym.spb_eyesclinic_project.domain.dto.billDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillTotalResponse {
//    private LocalDateTime dateDisease;
    private String date;
    private BigDecimal total;

}
