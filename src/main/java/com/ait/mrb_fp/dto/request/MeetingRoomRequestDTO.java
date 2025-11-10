package com.ait.mrb_fp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomRequestDTO {

    @NotBlank(message = "Office Id required")
    private String officeId;

    @NotBlank(message = "Room Name required")
    private String roomName;

    @NotBlank(message = "Capacity required")
    private int capacity;

    @NotBlank(message = "Room type required")
    private String roomType;
}
