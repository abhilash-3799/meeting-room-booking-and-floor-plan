package com.ait.mrb_fp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBookingRequestDTO {

    @NotBlank(message = "Seat id required")
    private String seatId;

    @NotBlank(message = "Employee id required")
    private String employeeId;

    private LocalDateTime seatBookingDate;
    private String status;
}
