package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomResponseDTO;
import com.ait.mrb_fp.service.MeetingRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/meeting-room")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @GetMapping
    public List<MeetingRoomResponseDTO> getAll() {
        log.info("Fetching all meeting rooms");
        return meetingRoomService.getAll();
    }

    @GetMapping("/{id}")
    public MeetingRoomResponseDTO getById(@PathVariable String id) {
        log.info("Fetching meeting room by ID: {}", id);
        return meetingRoomService.getById(id);
    }

    @PostMapping
    public MeetingRoomResponseDTO create(@RequestBody MeetingRoomRequestDTO request) {
        log.info("Creating new meeting room: {}", request);
        return meetingRoomService.create(request);
    }

    @PutMapping("/{id}")
    public MeetingRoomResponseDTO update(@PathVariable String id, @RequestBody MeetingRoomRequestDTO request) {
        log.info("Updating meeting room ID: {}", id);
        return meetingRoomService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting meeting room ID: {}", id);
        meetingRoomService.delete(id);
        log.info("Meeting room {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}
