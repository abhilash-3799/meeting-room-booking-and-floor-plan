package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.QueueRequestDTO;
import com.ait.mrb_fp.dto.response.QueueResponseDTO;
import com.ait.mrb_fp.service.QueueService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping
    public QueueResponseDTO create(@RequestBody QueueRequestDTO dto) {
        return queueService.create(dto);
    }

    @GetMapping
    public List<QueueResponseDTO> getAll() {
        return queueService.getAll();
    }

    @GetMapping("/{id}")
    public QueueResponseDTO getById(@PathVariable String id) {
        return queueService.getById(id);
    }

    @PutMapping("/{id}")
    public QueueResponseDTO update(@PathVariable String id, @RequestBody QueueRequestDTO dto) {
        return queueService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        queueService.delete(id);
    }
}
