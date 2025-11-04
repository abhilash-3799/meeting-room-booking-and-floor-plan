package com.ait.mrb_fp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueRequestDTO {
    private String officeId;
    private String queueName;
    private int totalSeats;
}
