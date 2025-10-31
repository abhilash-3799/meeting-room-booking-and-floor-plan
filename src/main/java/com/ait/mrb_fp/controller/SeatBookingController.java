package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
import com.ait.mrb_fp.service.SeatBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/seat-booking")
public class SeatBookingController {

    private final SeatBookingService seatBookingService;

    public SeatBookingController(SeatBookingService seatBookingService) {
        this.seatBookingService = seatBookingService;
    }

    @PostMapping
    public SeatBookingResponseDTO create(@RequestBody SeatBookingRequestDTO dto) {
        log.info("Creating new seat booking: {}", dto);
        return seatBookingService.create(dto);
    }

    @GetMapping
    public List<SeatBookingResponseDTO> getAll() {
        log.info("Fetching all seat bookings");
        return seatBookingService.getAll();
    }

    @GetMapping("/{id}")
    public SeatBookingResponseDTO getById(@PathVariable String id) {
        log.info("Fetching seat booking by ID: {}", id);
        return seatBookingService.getById(id);
    }

    @PutMapping("/{id}")
    public SeatBookingResponseDTO update(@PathVariable String id, @RequestBody SeatBookingRequestDTO dto) {
        log.info("Updating seat booking ID: {}", id);
        return seatBookingService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Deleting seat booking ID: {}", id);
        seatBookingService.delete(id);
        log.info("Seat booking {} deleted successfully", id);
    }


    // ✅ Bulk booking for Team Leads
    @PostMapping("/bulk/{teamLeadId}")
    public List<SeatBookingResponseDTO> createBulk(@PathVariable String teamLeadId,
                                                   @RequestBody List<SeatBookingRequestDTO> dtos) {
        return seatBookingService.createBulk(dtos, teamLeadId);
    }
}
