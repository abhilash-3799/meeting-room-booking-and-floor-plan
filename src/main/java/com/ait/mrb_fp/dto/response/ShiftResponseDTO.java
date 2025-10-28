package com.ait.mrb_fp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftResponseDTO {
    private String shiftId;
    private String shiftName;
    private String startTime;
    private String endTime;
    private String description;
    private boolean isActive;
}
