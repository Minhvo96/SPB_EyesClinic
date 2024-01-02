package com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.ref.PhantomReference;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingRequest {

    private String idEyeCategory;

    private String idCustomer;

    private String dateAppointment;

    private String status;

}
