package com.ait.mrb_fp.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingBookingDTO {

    private String meetingId;
    private String roomId;
    private String roomName;

    private String bookedByEmployeeId;
    private String bookedByEmployeeName;

    private LocalDate meetingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Integer noOfAttendees;
    private String purpose;
    private String status;
    private boolean isActive;
}
