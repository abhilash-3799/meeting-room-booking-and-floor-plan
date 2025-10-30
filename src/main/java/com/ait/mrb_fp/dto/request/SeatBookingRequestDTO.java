package com.ait.mrb_fp.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBookingRequestDTO {
    private String bookingId;
    private String seatId;
    private String employeeId;
    private LocalDateTime allocationDate;  // ✅ corrected
    private String status;
}
