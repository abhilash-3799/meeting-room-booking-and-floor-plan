package com.ait.mrb_fp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueResponseDTO {
    private String queueId;
    private String queueName;
    private String officeName;
    private int totalSeats;
    private String createdDate;
    private boolean isActive;
}
