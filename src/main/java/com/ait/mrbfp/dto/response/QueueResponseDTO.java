package com.ait.mrbfp.dto.response;

import lombok.Data;

@Data
public class QueueResponseDTO {

    private String queueId;
    private String officeName;
    private String queueName;
    private int totalSeats;
    private boolean active;
}
