package com.ait.mrb_fp.dto;

import lombok.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftDTO {

    private String shiftId;
    private String shiftName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private boolean isActive;
}
