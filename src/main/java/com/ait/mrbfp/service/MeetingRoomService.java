package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrbfp.dto.response.MeetingRoomResponseDTO;
import java.util.List;

public interface MeetingRoomService {
    MeetingRoomResponseDTO createRoom(MeetingRoomRequestDTO dto);
    List<MeetingRoomResponseDTO> getAllRooms();
    MeetingRoomResponseDTO getRoomById(String roomId);
    MeetingRoomResponseDTO updateRoom(String roomId, MeetingRoomRequestDTO dto);
    void deleteRoom(String roomId);
}
