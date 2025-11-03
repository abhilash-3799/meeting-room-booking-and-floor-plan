package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomResponseDTO;
import com.ait.mrb_fp.entity.MeetingRoom;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.mapper.MeetingRoomMapper;
import com.ait.mrb_fp.repository.MeetingRoomRepository;
import com.ait.mrb_fp.repository.OfficeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
            List<MeetingRoomResponseDTO> rooms = meetingRoomRepository.findAll()
                    .stream()
                    .map(MeetingRoomMapper::toResponse)
                    .collect(Collectors.toList());
            log.info("Total meeting rooms fetched: {}", rooms.size());
            return rooms;
        } catch (Exception ex) {
            log.error("Error fetching meeting rooms: {}", ex.getMessage());
            throw new RuntimeException("Error fetching meeting rooms.");
        }
    }

    @Override
    public MeetingRoomResponseDTO getById(String id) {
        log.info("Fetching meeting room by ID: {}", id);
        try {
            MeetingRoom room = meetingRoomRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Meeting Room not found: " + id));
            log.info("Meeting room found: {}", id);
            return MeetingRoomMapper.toResponse(room);
        } catch (Exception ex) {
            log.error("Error fetching meeting room with ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error fetching meeting room with ID: " + id);
        }
    }

    @Override
    public MeetingRoomResponseDTO create(MeetingRoomRequestDTO request) {
        log.info("Creating meeting room for office ID: {}", request.getOfficeId());
        try {
            Office office = officeRepository.findById(request.getOfficeId())
                    .orElseThrow(() -> new RuntimeException("Office not found: " + request.getOfficeId()));

            MeetingRoom meetingRoom = MeetingRoomMapper.toEntity(request, office);
            meetingRoom.setRoomId("MR" + System.currentTimeMillis());
            meetingRoomRepository.save(meetingRoom);

            log.info("Meeting room created successfully with ID: {}", meetingRoom.getRoomId());
            return MeetingRoomMapper.toResponse(meetingRoom);
        } catch (Exception ex) {
            log.error("Error creating meeting room: {}", ex.getMessage());
            throw new RuntimeException("Error creating meeting room.");
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

            log.info("Meeting room updated successfully: {}", id);
            return MeetingRoomMapper.toResponse(existing);
        } catch (Exception ex) {
            log.error("Error updating meeting room with ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error updating meeting room with ID: " + id);
        }
    }

    @Override
    public void delete(String id) {
        log.warn("Deleting meeting room with ID: {}", id);
        try {
            meetingRoomRepository.deleteById(id);
            log.info("Meeting room deleted successfully: {}", id);
        } catch (Exception ex) {
            log.error("Error deleting meeting room with ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error deleting meeting room with ID: " + id);
        }
    }
}
