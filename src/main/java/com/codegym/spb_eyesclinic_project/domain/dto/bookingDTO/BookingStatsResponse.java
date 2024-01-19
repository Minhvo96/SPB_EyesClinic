package com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingStatsResponse {

    private Integer completedCount;
    private Integer cancelledCount;

    public BookingStatsResponse(Integer completedCount, Integer cancelledCount) {
        this.completedCount = completedCount;
        this.cancelledCount = cancelledCount;
    }
}
