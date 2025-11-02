package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalTime;

@Data
@Schema(description = "Request object for creating or updating a shift")
public class ShiftRequestDTO {

    @NotBlank
    private String shiftName;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private String description;

    private boolean active = true;
}
