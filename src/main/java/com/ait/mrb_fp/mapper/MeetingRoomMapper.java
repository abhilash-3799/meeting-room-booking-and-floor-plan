package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.MeetingRoomDTO;
import com.ait.mrb_fp.entity.MeetingRoom;

public class MeetingRoomMapper {

    public static MeetingRoomDTO toDTO(MeetingRoom room) {
        if (room == null) return null;
        return MeetingRoomDTO.builder()
                .roomId(room.getRoomId())
                .officeId(room.getOfficeId())
                .roomName(room.getRoomName())
                .capacity(room.getCapacity())
                .roomType(room.getRoomType().name())
                .isActive(room.isActive())
                .build();
    }

    public static MeetingRoom toEntity(MeetingRoomDTO dto) {
        if (dto == null) return null;
        return MeetingRoom.builder()
                .roomId(dto.getRoomId())
                .officeId(dto.getOfficeId())
                .roomName(dto.getRoomName())
                .capacity(dto.getCapacity())
                .roomType(MeetingRoom.RoomType.valueOf(dto.getRoomType()))
                .isActive(dto.isActive())
                .build();
    }
}
