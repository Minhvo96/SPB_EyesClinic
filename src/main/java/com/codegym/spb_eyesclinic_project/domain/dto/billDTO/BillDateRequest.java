package com.codegym.spb_eyesclinic_project.domain.dto.billDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillDateRequest {
    private String month;
    private String year;
}
