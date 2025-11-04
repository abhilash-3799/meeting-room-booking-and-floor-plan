package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class MeetingRoomRequestDTO {

    @NotBlank
    private String roomName;

    @Min(1)
    private int capacity;

    @NotNull
    private RoomType roomType;

    @NotBlank
    private String officeId;

    public enum RoomType {
        BOARD_ROOM, CONFERENCE_ROOM, CABIN
    }
}
