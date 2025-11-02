package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MeetingRoomBookingRequestDTO {

    @NotBlank
    private String roomId;

    @NotBlank
    private String bookedByEmployeeId;

    @NotNull
    private LocalDate meetingDate;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @Schema(example = "Project discussion")
    private String purpose;
}
