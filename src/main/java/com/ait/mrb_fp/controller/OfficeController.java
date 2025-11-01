package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.OfficeRequestDTO;
import com.ait.mrb_fp.dto.response.OfficeResponseDTO;
import com.ait.mrb_fp.service.OfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/office")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Office Management",
        description = "APIs for managing office entities including creation, retrieval, updating, and soft deletion."
)
public class OfficeController {

    private final OfficeService officeService;

    @PostMapping
    @Operation(
            summary = "Create a new office",
            description = "Adds a new office record to the system."
    )
    public ResponseEntity<OfficeResponseDTO> createOffice(@RequestBody OfficeRequestDTO dto) {
        log.info("Creating office: {}", dto);
        return ResponseEntity.ok(officeService.createOffice(dto));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get office by ID",
            description = "Fetches details of a specific office using its unique ID."
    )
    public ResponseEntity<OfficeResponseDTO> getOfficeById(@PathVariable String id) {
        log.info("Fetching office ID: {}", id);
        return ResponseEntity.ok(officeService.getOfficeById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get all offices",
            description = "Retrieves a list of all offices currently available in the system."
    )
    public ResponseEntity<List<OfficeResponseDTO>> getAllOffices() {
        log.info("Fetching all offices");
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an office",
            description = "Updates details of an existing office by its ID."
    )
    public ResponseEntity<OfficeResponseDTO> updateOffice(@PathVariable String id, @RequestBody OfficeRequestDTO dto) {
        log.info("Updating office ID: {}", id);
        return ResponseEntity.ok(officeService.updateOffice(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deactivate (soft delete) an office",
            description = "Marks an office as inactive rather than permanently deleting it."
    )
    public ResponseEntity<String> deactivateOffice(@PathVariable String id) {
        log.info("Deactivating office ID: {}", id);
        officeService.deactivateOffice(id);
        return ResponseEntity.ok("Office marked as inactive (soft deleted).");
    }
}
