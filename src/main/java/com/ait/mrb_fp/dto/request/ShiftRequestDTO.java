package com.ait.mrb_fp.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftRequestDTO {

    @NotBlank(message = "Shift name is required")
    private String shiftName;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @Size(max = 200, message = "Description must not exceed 200 characters")
    private String description;
}

