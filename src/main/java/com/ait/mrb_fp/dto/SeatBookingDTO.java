package com.ait.mrb_fp.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBookingDTO {

    private String allocationId;

    private String seatId;
    private String seatNumber;

    private String employeeId;
    private String employeeName;

    private LocalDateTime allocationDate;
    private String status;

    private boolean isActive;
}
