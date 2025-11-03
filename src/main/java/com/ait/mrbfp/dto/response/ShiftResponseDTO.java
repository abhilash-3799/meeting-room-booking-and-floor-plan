package com.ait.mrbfp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalTime;

@Data
@Schema(description = "Response object for shift details")
public class ShiftResponseDTO {

    @Schema(description = "Unique ID of the shift", example = "SHIFT-23a7b8d4-9c20-4e22-9d3a-51b50c67a6a7")
    private String shiftId;

    @Schema(description = "Shift name", example = "Morning Shift")
    private String shiftName;

    @Schema(description = "Start time", example = "08:00")
    private LocalTime startTime;

    @Schema(description = "End time", example = "16:00")
    private LocalTime endTime;

    @Schema(description = "Description of the shift", example = "Regular morning hours")
    private String description;

    @Schema(description = "Active status", example = "true")
    private boolean active;
}
