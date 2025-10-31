package com.ait.mrb_fp.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomBookingRequestDTO {

    @NotBlank(message = "Room ID is required")
    private String roomId;

    @NotBlank(message = "BookedByEmployeeId is required")
    private String bookedByEmployeeId;


    @NotNull(message = "Meeting date is required")
    @FutureOrPresent(message = "Meeting date cannot be in the past")
    private LocalDate meetingDate;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotBlank(message = "Purpose is required")
    private String purpose;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "^(BOOKED|CANCELLED|COMPLETED|ON-GOING)$",
            message = "Status must be one of: BOOKED, CANCELLED, COMPLETED, or ON-GOING"
    )
    private String status;
}
