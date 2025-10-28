package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomResponseDTO;
import com.ait.mrb_fp.entity.MeetingRoom;
import com.ait.mrb_fp.entity.Office;

public class MeetingRoomMapper {

    public static MeetingRoom toEntity(MeetingRoomRequestDTO dto, Office office) {
        return MeetingRoom.builder()
                .office(office)
                .roomName(dto.getRoomName())
                .capacity(dto.getCapacity())
                .roomType(MeetingRoom.RoomType.valueOf(dto.getRoomType()))
                .isActive(true)
                .build();
    }

    public static MeetingRoomResponseDTO toResponse(MeetingRoom entity) {
        return MeetingRoomResponseDTO.builder()
                .roomId(entity.getRoomId())
                .roomName(entity.getRoomName())
                .officeName(entity.getOffice().getOfficeName())
                .capacity(entity.getCapacity())
                .roomType(entity.getRoomType().name())
                .isActive(entity.isActive())
                .build();
    }
}
