package com.ait.mrb_fp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequestDTO {
    private String officeId;
    private String seatNumber;
    private String assignedTeamId;
    private String queueId;
    private String seatStatus;
    private boolean isAvailable;
}
