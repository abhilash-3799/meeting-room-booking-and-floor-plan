package com.ait.mrbfp.controller;

import com.ait.mrbfp.dto.request.SeatRequestDTO;
import com.ait.mrbfp.dto.response.SeatResponseDTO;
import com.ait.mrbfp.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
@Tag(name = "Seat", description = "APIs for managing office seats")
public class SeatController {

    private final SeatService seatService;

    @Operation(summary = "Create a new seat")
    @PostMapping
    public ResponseEntity<SeatResponseDTO> create(@Valid @RequestBody SeatRequestDTO dto) {
        return ResponseEntity.ok(seatService.createSeat(dto));
    }

    @Operation(summary = "Get all seats")
    @GetMapping
    public ResponseEntity<List<SeatResponseDTO>> getAll() {
        return ResponseEntity.ok(seatService.getAllSeats());
    }

    @Operation(summary = "Get seat by ID")
    @GetMapping("/{seatId}")
    public ResponseEntity<SeatResponseDTO> getById(@PathVariable String seatId) {
        return ResponseEntity.ok(seatService.getSeatById(seatId));
    }

    @Operation(summary = "Get seats by office ID")
    @GetMapping("/office/{officeId}")
    public ResponseEntity<List<SeatResponseDTO>> getByOffice(@PathVariable String officeId) {
        return ResponseEntity.ok(seatService.getSeatsByOffice(officeId));
    }

    @Operation(summary = "Update seat details")
    @PutMapping("/{seatId}")
    public ResponseEntity<SeatResponseDTO> update(
            @PathVariable String seatId,
            @Valid @RequestBody SeatRequestDTO dto) {
        return ResponseEntity.ok(seatService.updateSeat(seatId, dto));
    }

    @Operation(summary = "Delete seat by ID")
    @DeleteMapping("/{seatId}")
    public ResponseEntity<Void> delete(@PathVariable String seatId) {
        seatService.deleteSeat(seatId);
        return ResponseEntity.noContent().build();
    }
}
