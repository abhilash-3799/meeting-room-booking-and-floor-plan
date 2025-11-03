package com.ait.mrb_fp.dto.response;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftResponseDTO {
    private String shiftId;
    private String shiftName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private boolean isActive;
}
