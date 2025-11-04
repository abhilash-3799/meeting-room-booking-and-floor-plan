package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.ShiftRequestDTO;
import com.ait.mrb_fp.dto.response.ShiftResponseDTO;
import com.ait.mrb_fp.service.ShiftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shift")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Shift Controller", description = "APIs for managing work shifts")
public class ShiftController {

    private final ShiftService shiftService;

    @Operation(summary = "Create a new shift", description = "Add a new shift")
    @PostMapping
    public ResponseEntity<ShiftResponseDTO> createShift(@RequestBody ShiftRequestDTO dto) {
        log.info("Creating new shift with name: {}", dto.getShiftName());
        return ResponseEntity.ok(shiftService.createShift(dto));
    }

    @Operation(summary = "Get shift by ID", description = "Fetch a shift using its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ShiftResponseDTO> getShiftById(@PathVariable String id) {
        log.info("Fetching shift with ID: {}", id);
        return ResponseEntity.ok(shiftService.getShiftById(id));
    }

    @Operation(summary = "Get all shifts", description = "Retrieve a list of all active shifts")
    @GetMapping
    public ResponseEntity<List<ShiftResponseDTO>> getAllShifts() {
        log.info("Fetching all active shifts");
        return ResponseEntity.ok(shiftService.getAllShifts());
    }

    @Operation(summary = "Update shift by ID", description = "Update an existing shift’s details")
    @PutMapping("/{id}")
    public ResponseEntity<ShiftResponseDTO> updateShift(@PathVariable String id, @RequestBody ShiftRequestDTO dto) {
        log.info("Updating shift with ID: {}", id);
        return ResponseEntity.ok(shiftService.updateShift(id, dto));
    }

    @Operation(summary = "Deactivate shift", description = "Soft delete a shift (mark inactive)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateShift(@PathVariable String id) {
        log.warn("Deactivating shift with ID: {}", id);
        shiftService.deactivateShift(id);
        return ResponseEntity.ok("Shift marked as inactive (soft deleted).");
    }
}
