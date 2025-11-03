package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalTime;

@Data
@Schema(description = "Request object for creating or updating a shift")
public class ShiftRequestDTO {

    @Schema(description = "Shift name", example = "Morning Shift")
    @NotBlank
    private String shiftName;

    @Schema(description = "Start time of the shift", example = "08:00")
    @NotNull
    private LocalTime startTime;

    @Schema(description = "End time of the shift", example = "16:00")
    @NotNull
    private LocalTime endTime;

    @Schema(description = "Shift description", example = "Regular morning hours")
    private String description;

    @Schema(description = "Whether the shift is active", example = "true")
    private boolean active = true;
}
