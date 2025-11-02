package com.ait.mrbfp.dto.response;

import lombok.Data;

@Data
public class SeatResponseDTO {

    private String seatId;
    private String officeName;
    private String seatNumber;
    private String seatStatus;
    private String teamName;
    private String queueName;
    private boolean isAvailable;
    private boolean active;
}
