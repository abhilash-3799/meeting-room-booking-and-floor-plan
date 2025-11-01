package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
import com.ait.mrb_fp.service.SeatBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/seat-booking")
@Tag(name = "Seat Booking Management", description = "APIs for handling individual and bulk seat bookings.")
public class SeatBookingController {

    private final SeatBookingService seatBookingService;

    public SeatBookingController(SeatBookingService seatBookingService) {
        this.seatBookingService = seatBookingService;
    }

    @PostMapping
    @Operation(summary = "Create a seat booking", description = "Creates a new seat booking entry.")
    public SeatBookingResponseDTO create(@RequestBody SeatBookingRequestDTO dto) {
        log.info("Creating seat booking: {}", dto);
        return seatBookingService.create(dto);
    }

    @GetMapping
    @Operation(summary = "Get all seat bookings", description = "Fetches all seat bookings.")
    public List<SeatBookingResponseDTO> getAll() {
        log.info("Fetching all seat bookings");
        return seatBookingService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get seat booking by ID", description = "Retrieves details of a seat booking by ID.")
    public SeatBookingResponseDTO getById(@PathVariable String id) {
        log.info("Fetching seat booking by ID: {}", id);
        return seatBookingService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update seat booking", description = "Updates an existing seat booking record.")
    public SeatBookingResponseDTO update(@PathVariable String id, @RequestBody SeatBookingRequestDTO dto) {
        log.info("Updating seat booking with ID: {}", id);
        return seatBookingService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete seat booking", description = "Deletes a seat booking by ID.")
    public void delete(@PathVariable String id) {
        log.info("Deleting seat booking with ID: {}", id);
        seatBookingService.delete(id);
    }

    @PostMapping("/bulk/{teamLeadId}")
    @Operation(summary = "Bulk seat booking", description = "Creates multiple seat bookings for a specific team lead.")
    public List<SeatBookingResponseDTO> createBulk(
            @PathVariable String teamLeadId,
            @RequestBody List<SeatBookingRequestDTO> dtos) {
        log.info("Creating bulk seat bookings for team lead: {}", teamLeadId);
        return seatBookingService.createBulk(dtos, teamLeadId);
    }
}
