package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrbfp.dto.response.MeetingRoomResponseDTO;
import com.ait.mrbfp.entity.MeetingRoom;
import com.ait.mrbfp.entity.Office;
import com.ait.mrbfp.mapper.MeetingRoomMapper;
import com.ait.mrbfp.repository.MeetingRoomRepository;
import com.ait.mrbfp.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;
    private final OfficeRepository officeRepository;

    @Override
    public MeetingRoomResponseDTO createRoom(MeetingRoomRequestDTO dto) {
        log.info("Creating meeting room: {}", dto.getRoomName());
        if (meetingRoomRepository.existsByRoomName(dto.getRoomName())) {
            log.error("Room name already exists: {}", dto.getRoomName());
            throw new RuntimeException("Room name already exists");
        }

        Office office = officeRepository.findById(dto.getOfficeId())
                .orElseThrow(() -> new RuntimeException("Office not found with ID: " + dto.getOfficeId()));

        MeetingRoom room = MeetingRoomMapper.toEntity(dto, office);
        meetingRoomRepository.save(room);
        log.info("Meeting room created successfully with ID: {}", room.getRoomId());
        return MeetingRoomMapper.toResponse(room);
    }

    @Override
    public List<MeetingRoomResponseDTO> getAllRooms() {
        log.info("Fetching all meeting rooms...");
        return meetingRoomRepository.findAll()
                .stream()
                .map(MeetingRoomMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingRoomResponseDTO getRoomById(String roomId) {
        log.info("Fetching meeting room with ID: {}", roomId);
        MeetingRoom room = meetingRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Meeting room not found with ID: " + roomId));
        return MeetingRoomMapper.toResponse(room);
    }

    @Override
    public MeetingRoomResponseDTO updateRoom(String roomId, MeetingRoomRequestDTO dto) {
        log.info("Updating meeting room with ID: {}", roomId);
        MeetingRoom existing = meetingRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Meeting room not found with ID: " + roomId));

        Office office = officeRepository.findById(dto.getOfficeId())
                .orElseThrow(() -> new RuntimeException("Office not found with ID: " + dto.getOfficeId()));

        MeetingRoomMapper.updateEntity(existing, dto, office);
        meetingRoomRepository.save(existing);
        log.info("Meeting room updated successfully with ID: {}", existing.getRoomId());
        return MeetingRoomMapper.toResponse(existing);
    }

    @Override
    public void deleteRoom(String roomId) {
        log.warn("Deleting meeting room with ID: {}", roomId);
        meetingRoomRepository.deleteById(roomId);
    }
}
