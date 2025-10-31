package com.ait.mrb_fp.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequestDTO {

    @NotBlank(message = "Office ID is required")
    private String officeId;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    private String assignedTeamId;

    private String queueId;

    @NotBlank(message = "Seat status is required")
    @Pattern(regexp = "^(AVAILABLE|OCCUPIED|MAINTENANCE)$",
            message = "Seat status must be one of: AVAILABLE, OCCUPIED, or MAINTENANCE")
    private String seatStatus;

    @AssertTrue(message = "Seat must be marked available if status is AVAILABLE")
    private boolean isAvailable;
}
