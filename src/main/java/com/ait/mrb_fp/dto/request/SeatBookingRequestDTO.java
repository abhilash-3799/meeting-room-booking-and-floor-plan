package com.ait.mrb_fp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBookingRequestDTO {
    private String seatId;
    private String employeeId;
    private String allocationDate;
    private String status;
}
