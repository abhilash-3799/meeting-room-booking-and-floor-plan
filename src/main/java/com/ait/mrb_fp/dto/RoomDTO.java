package com.ait.mrb_fp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private String roomId;
    private String officeId;
    private String officeName;
    private String roomName;
    private int capacity;
    private String roomType;
    private boolean isActive;
}
