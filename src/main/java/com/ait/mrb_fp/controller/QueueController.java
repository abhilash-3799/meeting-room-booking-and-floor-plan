package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.QueueRequestDTO;
import com.ait.mrb_fp.dto.response.QueueResponseDTO;
import com.ait.mrb_fp.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping
    public QueueResponseDTO create(@RequestBody QueueRequestDTO dto) {
        log.info("Creating queue: {}", dto);
        return queueService.create(dto);
    }

    @GetMapping
    public List<QueueResponseDTO> getAll() {
        log.info("Fetching all queues");
        return queueService.getAll();
    }

    @GetMapping("/{id}")
    public QueueResponseDTO getById(@PathVariable String id) {
        log.info("Fetching queue by ID: {}", id);
        return queueService.getById(id);
    }

    @PutMapping("/{id}")
    public QueueResponseDTO update(@PathVariable String id, @RequestBody QueueRequestDTO dto) {
        log.info("Updating queue ID: {}", id);
        return queueService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Deleting queue ID: {}", id);
        queueService.delete(id);
        log.info("Queue {} deleted successfully", id);
    }
}
