package com.ait.mrbfp.controller;

import com.ait.mrbfp.dto.request.ShiftRequestDTO;
import com.ait.mrbfp.dto.response.ShiftResponseDTO;
import com.ait.mrbfp.service.ShiftServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@RequiredArgsConstructor
@Tag(name = "Shift Management", description = "APIs for managing shift schedules")
public class ShiftController {

    private final ShiftServiceImpl shiftService;

    @Operation(summary = "Create a new shift")
    @PostMapping
    public ResponseEntity<ShiftResponseDTO> createShift(@Valid @RequestBody ShiftRequestDTO requestDTO) {
        return ResponseEntity.ok(shiftService.createShift(requestDTO));
    }

    @Operation(summary = "Get all shifts")
    @GetMapping
    public ResponseEntity<List<ShiftResponseDTO>> getAllShifts() {
        return ResponseEntity.ok(shiftService.getAllShifts());
    }

    @Operation(summary = "Get shift by ID")
    @GetMapping("/{shiftId}")
    public ResponseEntity<ShiftResponseDTO> getShiftById(@PathVariable String shiftId) {
        return ResponseEntity.ok(shiftService.getShiftById(shiftId));
    }

    @Operation(summary = "Update shift details")
    @PutMapping("/{shiftId}")
    public ResponseEntity<ShiftResponseDTO> updateShift(
            @PathVariable String shiftId,
            @Valid @RequestBody ShiftRequestDTO requestDTO) {
        return ResponseEntity.ok(shiftService.updateShift(shiftId, requestDTO));
    }

    @Operation(summary = "Delete shift by ID")
    @DeleteMapping("/{shiftId}")
    public ResponseEntity<Void> deleteShift(@PathVariable String shiftId) {
        shiftService.deleteShift(shiftId);
        return ResponseEntity.noContent().build();
    }
}
