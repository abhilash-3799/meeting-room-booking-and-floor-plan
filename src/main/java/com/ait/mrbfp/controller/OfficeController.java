package com.ait.mrbfp.controller;

import com.ait.mrbfp.dto.request.OfficeRequestDTO;
import com.ait.mrbfp.dto.response.OfficeResponseDTO;
import com.ait.mrbfp.service.OfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/offices")
@RequiredArgsConstructor
@Tag(name = "Office Management", description = "APIs for managing office details")
public class OfficeController {

    private final OfficeService officeService;

    @Operation(summary = "Create a new office")
    @PostMapping
    public ResponseEntity<OfficeResponseDTO> createOffice(@Valid @RequestBody OfficeRequestDTO requestDTO) {
        return ResponseEntity.ok(officeService.createOffice(requestDTO));
    }

    @Operation(summary = "Get all offices")
    @GetMapping
    public ResponseEntity<List<OfficeResponseDTO>> getAllOffices() {
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    @Operation(summary = "Get office by ID")
    @GetMapping("/{officeId}")
    public ResponseEntity<OfficeResponseDTO> getOfficeById(@PathVariable String officeId) {
        return ResponseEntity.ok(officeService.getOfficeById(officeId));
    }

    @Operation(summary = "Update office details")
    @PutMapping("/{officeId}")
    public ResponseEntity<OfficeResponseDTO> updateOffice(
            @PathVariable String officeId,
            @Valid @RequestBody OfficeRequestDTO requestDTO) {
        return ResponseEntity.ok(officeService.updateOffice(officeId, requestDTO));
    }

    @Operation(summary = "Delete office by ID")
    @DeleteMapping("/{officeId}")
    public ResponseEntity<Void> deleteOffice(@PathVariable String officeId) {
        officeService.deleteOffice(officeId);
        return ResponseEntity.noContent().build();
    }
}
