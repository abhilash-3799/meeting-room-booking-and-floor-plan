package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.SeatRequestDTO;
import com.ait.mrb_fp.dto.response.SeatResponseDTO;
import com.ait.mrb_fp.service.SeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/seat")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public SeatResponseDTO create(@RequestBody SeatRequestDTO dto) {
        log.info("Creating new seat: {}", dto);
        return seatService.create(dto);
    }

    @GetMapping
    public List<SeatResponseDTO> getAll() {
        log.info("Fetching all seats");
        return seatService.getAll();
    }

    @GetMapping("/{id}")
    public SeatResponseDTO getById(@PathVariable String id) {
        log.info("Fetching seat by ID: {}", id);
        return seatService.getById(id);
    }

    @PutMapping("/{id}")
    public SeatResponseDTO update(@PathVariable String id, @RequestBody SeatRequestDTO dto) {
        log.info("Updating seat ID: {}", id);
        return seatService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Deleting seat ID: {}", id);
        seatService.delete(id);
        log.info("Seat {} deleted", id);
    }
}
