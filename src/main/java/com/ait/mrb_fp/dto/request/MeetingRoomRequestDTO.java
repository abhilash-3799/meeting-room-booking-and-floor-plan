package com.ait.mrb_fp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomRequestDTO {
    private String officeId;
    private String roomName;
    private int capacity;
    private String roomType;
}
