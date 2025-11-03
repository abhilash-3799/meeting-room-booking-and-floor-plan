package com.ait.mrbfp.controller;

import com.ait.mrbfp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrbfp.dto.response.MeetingRoomResponseDTO;
import com.ait.mrbfp.service.MeetingRoomServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/meetingrooms")
@RequiredArgsConstructor
@Tag(name = "Meeting Room", description = "APIs for managing meeting rooms")
public class MeetingRoomController {

    private final MeetingRoomServiceImpl meetingRoomService;

    @Operation(summary = "Create a new meeting room")
    @PostMapping
    public ResponseEntity<MeetingRoomResponseDTO> createRoom(@Valid @RequestBody MeetingRoomRequestDTO dto) {
        return ResponseEntity.ok(meetingRoomService.createRoom(dto));
    }

    @Operation(summary = "Get all meeting rooms")
    @GetMapping
    public ResponseEntity<List<MeetingRoomResponseDTO>> getAllRooms() {
        return ResponseEntity.ok(meetingRoomService.getAllRooms());
    }

    @Operation(summary = "Get meeting room by ID")
    @GetMapping("/{roomId}")
    public ResponseEntity<MeetingRoomResponseDTO> getRoomById(@PathVariable String roomId) {
        return ResponseEntity.ok(meetingRoomService.getRoomById(roomId));
    }

    @Operation(summary = "Update meeting room details")
    @PutMapping("/{roomId}")
    public ResponseEntity<MeetingRoomResponseDTO> updateRoom(
            @PathVariable String roomId,
            @Valid @RequestBody MeetingRoomRequestDTO dto) {
        return ResponseEntity.ok(meetingRoomService.updateRoom(roomId, dto));
    }

    @Operation(summary = "Delete meeting room by ID")
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String roomId) {
        meetingRoomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }
}
