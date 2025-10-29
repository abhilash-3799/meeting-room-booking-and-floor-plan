package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
import com.ait.mrb_fp.service.SeatBookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-booking")
public class SeatBookingController {

    private final SeatBookingService seatBookingService;

    public SeatBookingController(SeatBookingService seatBookingService) {
        this.seatBookingService = seatBookingService;
    }

    @PostMapping
    public SeatBookingResponseDTO create(@RequestBody SeatBookingRequestDTO dto) {
        return seatBookingService.create(dto);
    }

    @GetMapping
    public List<SeatBookingResponseDTO> getAll() {
        return seatBookingService.getAll();
    }

    @GetMapping("/{id}")
    public SeatBookingResponseDTO getById(@PathVariable String id) {
        return seatBookingService.getById(id);
    }

    @PutMapping("/{id}")
    public SeatBookingResponseDTO update(@PathVariable String id, @RequestBody SeatBookingRequestDTO dto) {
        return seatBookingService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        seatBookingService.delete(id);
    }
}
