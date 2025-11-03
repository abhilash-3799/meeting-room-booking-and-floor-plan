package com.ait.mrbfp.controller;

import com.ait.mrbfp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrbfp.dto.response.MeetingRoomBookingResponseDTO;
import com.ait.mrbfp.service.MeetingRoomBookingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Meeting Room Booking", description = "APIs for managing meeting room bookings")
public class MeetingRoomBookingController {

    private final MeetingRoomBookingServiceImpl bookingService;

    @Operation(summary = "Create a meeting booking")
    @PostMapping
    public ResponseEntity<MeetingRoomBookingResponseDTO> createBooking(
            @Valid @RequestBody MeetingRoomBookingRequestDTO dto) {
        return ResponseEntity.ok(bookingService.createBooking(dto));
    }

    @Operation(summary = "Get all bookings")
    @GetMapping
    public ResponseEntity<List<MeetingRoomBookingResponseDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @Operation(summary = "Get booking by ID")
    @GetMapping("/{bookingId}")
    public ResponseEntity<MeetingRoomBookingResponseDTO> getBookingById(@PathVariable String bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @Operation(summary = "Update booking details")
    @PutMapping("/{bookingId}")
    public ResponseEntity<MeetingRoomBookingResponseDTO> updateBooking(
            @PathVariable String bookingId,
            @Valid @RequestBody MeetingRoomBookingRequestDTO dto) {
        return ResponseEntity.ok(bookingService.updateBooking(bookingId, dto));
    }

    @Operation(summary = "Cancel a booking")
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable String bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
