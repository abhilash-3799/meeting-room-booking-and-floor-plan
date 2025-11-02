package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SeatRequestDTO {

    @NotBlank
    private String officeId;

    @NotBlank
    private String seatNumber;

    private String assignedTeamId;

    private String queueId;

    @NotBlank
    private String seatStatus;

    @NotNull
    private Boolean isAvailable;
}
