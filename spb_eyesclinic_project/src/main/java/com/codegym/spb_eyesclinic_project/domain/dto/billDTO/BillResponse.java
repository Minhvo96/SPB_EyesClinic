package com.codegym.spb_eyesclinic_project.domain.dto.billDTO;

import com.codegym.spb_eyesclinic_project.domain.dto.medicineDTO.MedicineResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.response.OptionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillResponse {

    private LocalDateTime dateDisease;

    private OptionResponse receptionist;

    private List<MedicineResponse> medicinesList;

}
