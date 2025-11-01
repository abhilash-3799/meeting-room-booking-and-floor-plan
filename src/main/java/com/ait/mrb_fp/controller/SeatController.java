package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.SeatRequestDTO;
import com.ait.mrb_fp.dto.response.SeatResponseDTO;
import com.ait.mrb_fp.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/seat")
@Tag(name = "Seat Management", description = "Endpoints for managing seats.")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @Operation(summary = "Create new seat")
    @PostMapping
    public SeatResponseDTO create(@RequestBody SeatRequestDTO dto) {
        log.info("Creating seat: {}", dto);
        return seatService.create(dto);
    }

    @Operation(summary = "Get all seats")
    @GetMapping
    public List<SeatResponseDTO> getAll() {
        log.info("Fetching all seats");
        return seatService.getAll();
    }

    @Operation(summary = "Get seat by ID")
    @GetMapping("/{id}")
    public SeatResponseDTO getById(@PathVariable String id) {
        log.info("Fetching seat by ID: {}", id);
        return seatService.getById(id);
    }

    @Operation(summary = "Update seat details")
    @PutMapping("/{id}")
    public SeatResponseDTO update(@PathVariable String id, @RequestBody SeatRequestDTO dto) {
        log.info("Updating seat with ID: {}", id);
        return seatService.update(id, dto);
    }

    @Operation(summary = "Delete seat")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting seat with ID: {}", id);
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
