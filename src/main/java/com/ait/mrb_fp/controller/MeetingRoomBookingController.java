package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomBookingResponseDTO;
import com.ait.mrb_fp.service.MeetingRoomBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/meeting-room-booking")
@Slf4j
@Tag(
        name = "Meeting Room Booking Management",
        description = "APIs for creating, retrieving, updating, and deleting meeting room bookings."
)
public class MeetingRoomBookingController {

    private final MeetingRoomBookingService meetingRoomBookingService;

    public MeetingRoomBookingController(MeetingRoomBookingService meetingRoomBookingService) {
        this.meetingRoomBookingService = meetingRoomBookingService;
    }

    @GetMapping
    @Operation(
            summary = "Get all meeting room bookings",
            description = "Retrieves a list of all existing meeting room bookings."
    )
    public List<MeetingRoomBookingResponseDTO> getAll() {
        log.info("Fetching all meeting room bookings");
        return meetingRoomBookingService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get meeting room booking by ID",
            description = "Fetches a specific meeting room booking using its unique ID."
    )
    public MeetingRoomBookingResponseDTO getById(@PathVariable String id) {
        log.info("Fetching meeting room booking ID: {}", id);
        return meetingRoomBookingService.getById(id);
    }

    @PostMapping
    @Operation(
            summary = "Create a new meeting room booking",
            description = "Creates and saves a new meeting room booking with the provided details."
    )
    public MeetingRoomBookingResponseDTO create(@RequestBody MeetingRoomBookingRequestDTO request) {
        log.info("Creating meeting room booking: {}", request);
        return meetingRoomBookingService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing meeting room booking",
            description = "Updates the details of an existing meeting room booking by ID."
    )
    public MeetingRoomBookingResponseDTO update(@PathVariable String id, @RequestBody MeetingRoomBookingRequestDTO request) {
        log.info("Updating meeting room booking ID: {}", id);
        return meetingRoomBookingService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a meeting room booking",
            description = "Deletes an existing meeting room booking by ID."
    )
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting meeting room booking ID: {}", id);
        meetingRoomBookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
