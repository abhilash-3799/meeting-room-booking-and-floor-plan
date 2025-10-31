package com.ait.mrb_fp.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomRequestDTO {

    @NotBlank(message = "Office ID is required")
    private String officeId;

    @NotBlank(message = "Room name is required")

    private String roomName;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 7, message = "Capacity cannot exceed 7")
    private int capacity;

    @NotBlank(message = "Room type is required")
    @Pattern(
            regexp = "^(CONFERENCE|MEETING|CABIN|DISCUSSION)$",
            message = "Room type must be one of: CONFERENCE, MEETING, CABIN, DISCUSSION"
    )
    private String roomType;
}
