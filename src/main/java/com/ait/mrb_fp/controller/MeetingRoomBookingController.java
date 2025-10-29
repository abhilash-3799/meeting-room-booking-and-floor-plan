package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomBookingResponseDTO;
import com.ait.mrb_fp.service.MeetingRoomBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/meeting-room-booking")
public class MeetingRoomBookingController {

    private final MeetingRoomBookingService meetingRoomBookingService;

    public MeetingRoomBookingController(MeetingRoomBookingService meetingRoomBookingService) {
        this.meetingRoomBookingService = meetingRoomBookingService;
    }

    @GetMapping
    public List<MeetingRoomBookingResponseDTO> getAll() {
        return meetingRoomBookingService.getAll();
    }

    @GetMapping("/{id}")
    public MeetingRoomBookingResponseDTO getById(@PathVariable String id) {
        return meetingRoomBookingService.getById(id);
    }

    @PostMapping
    public MeetingRoomBookingResponseDTO create(@RequestBody MeetingRoomBookingRequestDTO request) {
        return meetingRoomBookingService.create(request);
    }

    @PutMapping("/{id}")
    public MeetingRoomBookingResponseDTO update(@PathVariable String id, @RequestBody MeetingRoomBookingRequestDTO request) {
        return meetingRoomBookingService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        meetingRoomBookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
