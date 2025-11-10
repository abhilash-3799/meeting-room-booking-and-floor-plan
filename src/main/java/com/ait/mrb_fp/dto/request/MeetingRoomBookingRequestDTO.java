package com.ait.mrb_fp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomBookingRequestDTO {

    @NotBlank(message = "Employee Id required")
    private String bookedByEmployeeId;

    @NotBlank(message = "Meeting Date Required")
    private LocalDate meetingDate;

    @NotBlank(message = "Start time required")
    private LocalDateTime startTime;

    @NotBlank(message = "End time required")
    private LocalDateTime endTime;

    @NotBlank(message = "Purpose Required")
    private String purpose;

    private String status;
}
