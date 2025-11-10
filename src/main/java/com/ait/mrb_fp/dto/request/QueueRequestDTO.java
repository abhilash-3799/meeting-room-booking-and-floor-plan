package com.ait.mrb_fp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueRequestDTO {

    @NotBlank(message = "Office Id Required")
    private String officeId;

    @NotBlank(message = "Queue Name Required")
    private String queueName;

    @NotBlank(message = "Total Seats in queue required")
    private int totalSeats;
}
