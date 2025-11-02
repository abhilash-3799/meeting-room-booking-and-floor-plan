package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequestDTO {

    @Schema(description = "Employee ID to whom the notification belongs", example = "EMP-12345")
    @NotBlank
    private String employeeId;

    @Schema(description = "Title of the notification", example = "Meeting Reminder")
    @NotBlank
    private String title;

    @Schema(description = "Detailed notification message", example = "You have a meeting scheduled at 10:00 AM.")
    @NotBlank
    private String message;
}
