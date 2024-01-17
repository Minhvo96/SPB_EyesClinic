package com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookingShowDetailResponse {

    private Long id;

    private String idEyeCategory;

    private Long idCustomer;

    private LocalDate dateBooking;

    private String timeBooking;

    private String status;
}
