package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.OfficeRequestDTO;
import com.ait.mrb_fp.dto.response.OfficeResponseDTO;
import com.ait.mrb_fp.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @PostMapping
    public ResponseEntity<OfficeResponseDTO> createOffice(@RequestBody OfficeRequestDTO dto) {
        return ResponseEntity.ok(officeService.createOffice(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeResponseDTO> getOfficeById(@PathVariable String id) {
        return ResponseEntity.ok(officeService.getOfficeById(id));
    }

    @GetMapping
    public ResponseEntity<List<OfficeResponseDTO>> getAllOffices() {
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeResponseDTO> updateOffice(@PathVariable String id, @RequestBody OfficeRequestDTO dto) {
        return ResponseEntity.ok(officeService.updateOffice(id, dto));
    }

    // ✅ Soft Delete (sets isActive = false)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateOffice(@PathVariable String id) {
        officeService.deactivateOffice(id);
        return ResponseEntity.ok("Office marked as inactive (soft deleted).");
    }
}
