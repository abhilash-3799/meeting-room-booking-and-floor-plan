package com.ait.mrb_fp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingBookingResponseDTO {
    private String meetingId;
    private String roomName;
    private String bookedBy;
    private String meetingDate;
    private String startTime;
    private String endTime;
    private Integer noOfAttendees;
    private String purpose;
    private String status;
    private boolean isActive;
}
