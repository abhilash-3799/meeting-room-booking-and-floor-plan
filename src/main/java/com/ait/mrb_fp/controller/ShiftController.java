package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.ShiftRequestDTO;
import com.ait.mrb_fp.dto.response.ShiftResponseDTO;
import com.ait.mrb_fp.service.ShiftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/shift")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    @PostMapping
    public ResponseEntity<ShiftResponseDTO> createShift(@RequestBody ShiftRequestDTO dto) {
        log.info("Creating new shift: {}", dto);
        ShiftResponseDTO response = shiftService.createShift(dto);
        log.info("Shift created successfully with ID: {}", response.getShiftId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftResponseDTO> getShiftById(@PathVariable String id) {
        log.info("Fetching shift by ID: {}", id);
        return ResponseEntity.ok(shiftService.getShiftById(id));
    }

    @GetMapping
    public ResponseEntity<List<ShiftResponseDTO>> getAllShifts() {
        log.info("Fetching all shifts");
        return ResponseEntity.ok(shiftService.getAllShifts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftResponseDTO> updateShift(@PathVariable String id, @RequestBody ShiftRequestDTO dto) {
        log.info("Updating shift ID: {}", id);
        return ResponseEntity.ok(shiftService.updateShift(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateShift(@PathVariable String id) {
        log.info("Deactivating shift ID: {}", id);
        shiftService.deactivateShift(id);
        return ResponseEntity.ok("Shift marked as inactive (soft deleted).");
    }
}
