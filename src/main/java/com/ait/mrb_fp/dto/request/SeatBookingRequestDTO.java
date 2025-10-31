package com.ait.mrb_fp.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBookingRequestDTO {

    @NotBlank(message = "Booking ID is required")
    private String bookingId;

    @NotBlank(message = "Seat ID is required")
    private String seatId;

    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @NotNull(message = "Allocation date is required")
    @FutureOrPresent(message = "Allocation date cannot be in the past")
    private LocalDateTime allocationDate;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(PENDING|CONFIRMED|CANCELLED)$",
            message = "Status must be one of: PENDING, CONFIRMED, or CANCELLED")
    private String status;
}
