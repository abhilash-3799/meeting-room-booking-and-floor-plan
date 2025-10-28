package com.ait.mrb_fp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDTO {

    private String seatId;
    private String officeId;
    private String officeName;

    private String seatNumber;
    private String seatStatus;
    private String assignedTeamId;
    private String assignedTeamName;
    private String queueId;
    private String queueName;

    private boolean isAvailable;
    private boolean isActive;
}
