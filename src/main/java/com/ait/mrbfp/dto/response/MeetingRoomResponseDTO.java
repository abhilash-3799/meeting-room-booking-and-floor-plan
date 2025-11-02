package com.ait.mrbfp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MeetingRoomResponseDTO {

    private String roomId;
    private String roomName;
    private int capacity;
    private String roomType;
    private String roomStatus;
    private boolean active;
    private String officeName;
}
