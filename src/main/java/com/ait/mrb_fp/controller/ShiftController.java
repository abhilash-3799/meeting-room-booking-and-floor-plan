package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.ShiftRequestDTO;
import com.ait.mrb_fp.dto.response.ShiftResponseDTO;
import com.ait.mrb_fp.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shift")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    @PostMapping
    public ResponseEntity<ShiftResponseDTO> createShift(@RequestBody ShiftRequestDTO dto) {
        return ResponseEntity.ok(shiftService.createShift(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftResponseDTO> getShiftById(@PathVariable String id) {
        return ResponseEntity.ok(shiftService.getShiftById(id));
    }

    @GetMapping
    public ResponseEntity<List<ShiftResponseDTO>> getAllShifts() {
        return ResponseEntity.ok(shiftService.getAllShifts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftResponseDTO> updateShift(@PathVariable String id, @RequestBody ShiftRequestDTO dto) {
        return ResponseEntity.ok(shiftService.updateShift(id, dto));
    }

    // ✅ Soft Delete (sets isActive = false)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateShift(@PathVariable String id) {
        shiftService.deactivateShift(id);
        return ResponseEntity.ok("Shift marked as inactive (soft deleted).");
    }
}
