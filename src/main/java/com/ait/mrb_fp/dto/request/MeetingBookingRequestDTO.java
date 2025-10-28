package com.ait.mrb_fp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingBookingRequestDTO {
    private String roomId;
    private String bookedByEmployeeId;
    private String meetingDate;
    private String startTime;
    private String endTime;
    private Integer noOfAttendees;
    private String purpose;
    private String status;
}
