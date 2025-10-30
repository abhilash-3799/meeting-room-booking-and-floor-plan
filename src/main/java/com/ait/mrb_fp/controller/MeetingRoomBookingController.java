package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomBookingResponseDTO;
import com.ait.mrb_fp.service.MeetingRoomBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/meeting-room-booking")
public class MeetingRoomBookingController {

    private final MeetingRoomBookingService meetingRoomBookingService;

    public MeetingRoomBookingController(MeetingRoomBookingService meetingRoomBookingService) {
        this.meetingRoomBookingService = meetingRoomBookingService;
    }

    @GetMapping
    public List<MeetingRoomBookingResponseDTO> getAll() {
        log.info("Fetching all meeting room bookings");
        return meetingRoomBookingService.getAll();
    }

    @GetMapping("/{id}")
    public MeetingRoomBookingResponseDTO getById(@PathVariable String id) {
        log.info("Fetching meeting room booking by ID: {}", id);
        return meetingRoomBookingService.getById(id);
    }

    @PostMapping
    public MeetingRoomBookingResponseDTO create(@RequestBody MeetingRoomBookingRequestDTO request) {
        log.info("Creating meeting room booking: {}", request);
        return meetingRoomBookingService.create(request);
    }

    @PutMapping("/{id}")
    public MeetingRoomBookingResponseDTO update(@PathVariable String id, @RequestBody MeetingRoomBookingRequestDTO request) {
        log.info("Updating meeting room booking ID: {}", id);
        return meetingRoomBookingService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting meeting room booking ID: {}", id);
        meetingRoomBookingService.delete(id);
        log.info("Meeting room booking {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}
