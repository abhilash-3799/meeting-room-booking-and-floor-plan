package com.ait.mrb_fp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBookingResponseDTO {
    private String allocationId;
    private String seatNumber;
    private String employeeName;
    private String allocationDate;
    private String status;
    private boolean isActive;
}
