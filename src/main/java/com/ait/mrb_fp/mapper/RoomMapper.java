package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.RoomDTO;
import com.ait.mrb_fp.entity.Room;

public class RoomMapper {

    public static RoomDTO toDTO(Room room) {
        if (room == null) return null;
        return RoomDTO.builder()
                .roomId(room.getRoomId())
                .officeId(room.getOfficeId())
                .roomName(room.getRoomName())
                .capacity(room.getCapacity())
                .roomType(room.getRoomType().name())
                .isActive(room.isActive())
                .build();
    }

    public static Room toEntity(RoomDTO dto) {
        if (dto == null) return null;
        return Room.builder()
                .roomId(dto.getRoomId())
                .officeId(dto.getOfficeId())
                .roomName(dto.getRoomName())
                .capacity(dto.getCapacity())
                .roomType(Room.RoomType.valueOf(dto.getRoomType()))
                .isActive(dto.isActive())
                .build();
    }
}
