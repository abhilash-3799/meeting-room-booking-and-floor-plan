package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomResponseDTO;
import com.ait.mrb_fp.service.MeetingRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/meeting-room")
@Slf4j
@Tag(
        name = "Meeting Room Management",
        description = "APIs for creating, retrieving, updating, and deleting meeting rooms."
)
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @GetMapping
    @Operation(
            summary = "Get all meeting rooms",
            description = "Retrieves a list of all meeting rooms available in the system."
    )
    public List<MeetingRoomResponseDTO> getAll() {
        log.info("Fetching all meeting rooms");
        return meetingRoomService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get meeting room by ID",
            description = "Fetches details of a specific meeting room using its unique ID."
    )
    public MeetingRoomResponseDTO getById(@PathVariable String id) {
        log.info("Fetching meeting room ID: {}", id);
        return meetingRoomService.getById(id);
    }

    @PostMapping
    @Operation(
            summary = "Create a new meeting room",
            description = "Adds a new meeting room record to the system."
    )
    public MeetingRoomResponseDTO create(@RequestBody MeetingRoomRequestDTO request) {
        log.info("Creating meeting room: {}", request);
        return meetingRoomService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a meeting room",
            description = "Updates details of an existing meeting room by its ID."
    )
    public MeetingRoomResponseDTO update(@PathVariable String id, @RequestBody MeetingRoomRequestDTO request) {
        log.info("Updating meeting room ID: {}", id);
        return meetingRoomService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a meeting room",
            description = "Removes an existing meeting room from the system by its ID."
    )
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting meeting room ID: {}", id);
        meetingRoomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
