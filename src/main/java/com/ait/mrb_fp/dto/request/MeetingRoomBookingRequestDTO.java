package com.ait.mrb_fp.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomBookingRequestDTO {
    private String roomId;
    private String bookedByEmployeeId;
    private LocalDate meetingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private String status;
}
