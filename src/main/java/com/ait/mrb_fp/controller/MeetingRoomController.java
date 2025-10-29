package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomResponseDTO;
import com.ait.mrb_fp.service.MeetingRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/meeting-room")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @GetMapping
    public List<MeetingRoomResponseDTO> getAll() {
        return meetingRoomService.getAll();
    }

    @GetMapping("/{id}")
    public MeetingRoomResponseDTO getById(@PathVariable String id) {
        return meetingRoomService.getById(id);
    }

    @PostMapping
    public MeetingRoomResponseDTO create(@RequestBody MeetingRoomRequestDTO request) {
        return meetingRoomService.create(request);
    }

    @PutMapping("/{id}")
    public MeetingRoomResponseDTO update(@PathVariable String id, @RequestBody MeetingRoomRequestDTO request) {
        return meetingRoomService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        meetingRoomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
