package com.ait.mrbfp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalTime;

@Data
public class ShiftResponseDTO {

    private String shiftId;

    private String shiftName;

    private LocalTime startTime;

    private LocalTime endTime;

    private String description;

    private boolean active;
}
