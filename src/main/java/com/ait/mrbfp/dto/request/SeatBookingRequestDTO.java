package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SeatBookingRequestDTO {

    @NotBlank
    private String seatId;

    @NotBlank
    private String employeeId;

    @NotNull
    private LocalDateTime allocationDate;

    @NotBlank
    private String status;
}
