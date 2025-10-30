package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.OfficeRequestDTO;
import com.ait.mrb_fp.dto.response.OfficeResponseDTO;
import com.ait.mrb_fp.service.OfficeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/office")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @PostMapping
    public ResponseEntity<OfficeResponseDTO> createOffice(@RequestBody OfficeRequestDTO dto) {
        log.info("Creating new office: {}", dto);
        return ResponseEntity.ok(officeService.createOffice(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeResponseDTO> getOfficeById(@PathVariable String id) {
        log.info("Fetching office by ID: {}", id);
        return ResponseEntity.ok(officeService.getOfficeById(id));
    }

    @GetMapping
    public ResponseEntity<List<OfficeResponseDTO>> getAllOffices() {
        log.info("Fetching all offices");
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeResponseDTO> updateOffice(@PathVariable String id, @RequestBody OfficeRequestDTO dto) {
        log.info("Updating office ID: {}", id);
        return ResponseEntity.ok(officeService.updateOffice(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateOffice(@PathVariable String id) {
        log.info("Deactivating office ID: {}", id);
        officeService.deactivateOffice(id);
        return ResponseEntity.ok("Office marked as inactive (soft deleted).");
    }
}
