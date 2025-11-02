package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QueueRequestDTO {

    @NotBlank
    private String officeId;

    @NotBlank
    private String queueName;

    @NotNull
    private Integer totalSeats;
}
