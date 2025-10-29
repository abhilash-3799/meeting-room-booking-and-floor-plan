package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.SeatRequestDTO;
import com.ait.mrb_fp.dto.response.SeatResponseDTO;
import com.ait.mrb_fp.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public SeatResponseDTO create(@RequestBody SeatRequestDTO dto) {
        return seatService.create(dto);
    }

    @GetMapping
    public List<SeatResponseDTO> getAll() {
        return seatService.getAll();
    }

    @GetMapping("/{id}")
    public SeatResponseDTO getById(@PathVariable String id) {
        return seatService.getById(id);
    }

    @PutMapping("/{id}")
    public SeatResponseDTO update(@PathVariable String id, @RequestBody SeatRequestDTO dto) {
        return seatService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        seatService.delete(id);
    }
}
