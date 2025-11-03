package com.ait.mrbfp.controller;

import com.ait.mrbfp.dto.request.SeatBookingRequestDTO;
import com.ait.mrbfp.dto.response.SeatBookingResponseDTO;
import com.ait.mrbfp.service.SeatBookingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-bookings")
@RequiredArgsConstructor
@Tag(name = "Seat Booking", description = "APIs for allocating and releasing office seats")
public class SeatBookingController {

    private final SeatBookingServiceImpl seatBookingService;

    @Operation(summary = "Allocate a seat to an employee")
    @PostMapping
    public ResponseEntity<SeatBookingResponseDTO> create(@Valid @RequestBody SeatBookingRequestDTO dto) {
        return ResponseEntity.ok(seatBookingService.createBooking(dto));
    }

    @Operation(summary = "Get all seat bookings")
    @GetMapping
    public ResponseEntity<List<SeatBookingResponseDTO>> getAll() {
        return ResponseEntity.ok(seatBookingService.getAllBookings());
    }

    @Operation(summary = "Get booking by ID")
    @GetMapping("/{allocationId}")
    public ResponseEntity<SeatBookingResponseDTO> getById(@PathVariable String allocationId) {
        return ResponseEntity.ok(seatBookingService.getBookingById(allocationId));
    }

    @Operation(summary = "Get bookings by employee ID")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SeatBookingResponseDTO>> getByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(seatBookingService.getBookingsByEmployee(employeeId));
    }

    @Operation(summary = "Delete a seat booking")
    @DeleteMapping("/{allocationId}")
    public ResponseEntity<Void> delete(@PathVariable String allocationId) {
        seatBookingService.deleteBooking(allocationId);
        return ResponseEntity.noContent().build();
    }
}
