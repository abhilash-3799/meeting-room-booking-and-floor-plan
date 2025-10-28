package com.ait.mrb_fp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftRequestDTO {
    private String shiftName;
    private String startTime;
    private String endTime;
    private String description;
}
