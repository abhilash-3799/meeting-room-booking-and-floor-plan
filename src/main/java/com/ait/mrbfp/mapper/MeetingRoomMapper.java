package com.ait.mrbfp.mapper;

import com.ait.mrbfp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrbfp.dto.response.MeetingRoomResponseDTO;
import com.ait.mrbfp.entity.MeetingRoom;
import com.ait.mrbfp.entity.Office;
import com.ait.mrbfp.exception.BadRequestException;

public class MeetingRoomMapper {

    private MeetingRoomMapper() {}

    public static MeetingRoom toEntity(MeetingRoomRequestDTO dto, Office office) {
        MeetingRoom room = new MeetingRoom();
        room.setRoomName(dto.getRoomName());
        room.setCapacity(dto.getCapacity());
        room.setOffice(office);
        try {
            room.setRoomType(MeetingRoom.RoomType.valueOf(dto.getRoomType().name()));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid room type: " + dto.getRoomType());
        }
        room.setRoomStatus(MeetingRoom.MeetingRoomStatus.AVAILABLE);
        room.setActive(true);
        return room;
    }

    public static MeetingRoomResponseDTO toResponse(MeetingRoom room) {
        MeetingRoomResponseDTO dto = new MeetingRoomResponseDTO();
        dto.setRoomId(room.getRoomId());
        dto.setRoomName(room.getRoomName());
        dto.setCapacity(room.getCapacity());
        dto.setRoomType(room.getRoomType().name());
        dto.setRoomStatus(room.getRoomStatus().name());
        dto.setActive(room.isActive());
        dto.setOfficeName(room.getOffice() != null ? room.getOffice().getOfficeName() : null);
        return dto;
    }

    public static void updateEntity(MeetingRoom existing, MeetingRoomRequestDTO dto, Office office) {
        existing.setRoomName(dto.getRoomName());
        existing.setCapacity(dto.getCapacity());
        existing.setOffice(office);
        try {
            existing.setRoomType(MeetingRoom.RoomType.valueOf(dto.getRoomType().name()));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid room type: " + dto.getRoomType());
        }
    }
}
