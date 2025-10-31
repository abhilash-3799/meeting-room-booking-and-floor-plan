package com.ait.mrb_fp.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueRequestDTO {

    @NotBlank(message = "Office ID is required")
    private String officeId;

    @NotBlank(message = "Queue name is required")
    private String queueName;

    @Min(value = 1, message = "Total seats must be at least 1")
    @Max(value = 10, message = "Total seats in a queue cannot exceed 10")
    private int totalSeats;
}
