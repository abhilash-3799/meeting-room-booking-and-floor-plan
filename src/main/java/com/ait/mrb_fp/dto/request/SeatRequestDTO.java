package com.ait.mrb_fp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequestDTO {

    @NotBlank(message = "Office id required")
    private String officeId;

    @NotBlank(message = "Seat number id required")
    private String seatNumber;

    @NotBlank(message = "Team id required")
    private String assignedTeamId;

    @NotBlank(message = "Queue Id required")
    private String queueId;

    private String seatStatus;
    private boolean isAvailable;
}
