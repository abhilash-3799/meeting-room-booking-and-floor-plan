package com.ait.mrb_fp.dto.request;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftRequestDTO {
    private String shiftName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
}
