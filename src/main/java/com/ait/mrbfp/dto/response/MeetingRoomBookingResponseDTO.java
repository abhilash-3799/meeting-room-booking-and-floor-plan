package com.ait.mrbfp.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MeetingRoomBookingResponseDTO {

    private String meetingId;
    private String roomName;
    private String bookedByEmployeeName;
    private LocalDate meetingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private String status;
    private boolean active;
}
