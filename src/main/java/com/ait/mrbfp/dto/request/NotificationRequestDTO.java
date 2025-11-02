package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequestDTO {

    @NotBlank
    private String employeeId;

    @NotBlank
    private String title;

    @NotBlank
    private String message;
}
