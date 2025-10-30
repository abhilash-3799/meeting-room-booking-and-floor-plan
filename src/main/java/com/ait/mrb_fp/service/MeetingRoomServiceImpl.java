package com.ait.mrb_fp.service.impl;

import com.ait.mrb_fp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomResponseDTO;
import com.ait.mrb_fp.entity.MeetingRoom;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.mapper.MeetingRoomMapper;
import com.ait.mrb_fp.repository.MeetingRoomRepository;
import com.ait.mrb_fp.repository.OfficeRepository;
import com.ait.mrb_fp.service.MeetingRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;
    private final OfficeRepository officeRepository;

    public MeetingRoomServiceImpl(MeetingRoomRepository meetingRoomRepository, OfficeRepository officeRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
        this.officeRepository = officeRepository;
    }

    @Override
    public List<MeetingRoomResponseDTO> getAll() {
        log.info("Fetching all meeting rooms");
        try {
            return meetingRoomRepository.findAll()
                    .stream()
                    .map(MeetingRoomMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching meeting rooms: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public MeetingRoomResponseDTO getById(String id) {
        log.info("Fetching meeting room with ID: {}", id);
        try {
            MeetingRoom room = meetingRoomRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Meeting Room not found: " + id));
            return MeetingRoomMapper.toResponse(room);
        } catch (Exception e) {
            log.error("Error fetching meeting room ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public MeetingRoomResponseDTO create(MeetingRoomRequestDTO request) {
        log.info("Creating meeting room: {}", request.getRoomName());
        try {
            Office office = officeRepository.findById(request.getOfficeId())
                    .orElseThrow(() -> new RuntimeException("Office not found: " + request.getOfficeId()));
            MeetingRoom meetingRoom = MeetingRoomMapper.toEntity(request, office);
            meetingRoomRepository.save(meetingRoom);
            log.info("Meeting room created successfully with ID: {}", meetingRoom.getRoomId());
            return MeetingRoomMapper.toResponse(meetingRoom);
        } catch (Exception e) {
            log.error("Error creating meeting room: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public MeetingRoomResponseDTO update(String id, MeetingRoomRequestDTO request) {
        log.info("Updating meeting room with ID: {}", id);
        try {
            MeetingRoom existing = meetingRoomRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Meeting Room not found: " + id));

            Office office = officeRepository.findById(request.getOfficeId())
                    .orElseThrow(() -> new RuntimeException("Office not found: " + request.getOfficeId()));

            MeetingRoomMapper.updateEntity(existing, request, office);
            meetingRoomRepository.save(existing);
            log.info("Meeting room with ID {} updated successfully", id);
            return MeetingRoomMapper.toResponse(existing);
        } catch (Exception e) {
            log.error("Error updating meeting room ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(String id) {
        log.info("Deleting meeting room with ID: {}", id);
        try {
            meetingRoomRepository.deleteById(id);
            log.info("Meeting room with ID {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting meeting room ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
