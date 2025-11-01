package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.QueueRequestDTO;
import com.ait.mrb_fp.dto.response.QueueResponseDTO;
import com.ait.mrb_fp.service.QueueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/queue")
@Tag(name = "Queue Management", description = "APIs for managing office queues.")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping
    @Operation(summary = "Create a new queue", description = "Creates a new queue entry in the system.")
    public QueueResponseDTO create(@RequestBody QueueRequestDTO dto) {
        log.info("Creating new queue: {}", dto);
        return queueService.create(dto);
    }

    @GetMapping
    @Operation(summary = "Get all queues", description = "Retrieves all queue entries.")
    public List<QueueResponseDTO> getAll() {
        log.info("Fetching all queues");
        return queueService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get queue by ID", description = "Retrieves queue details by its unique identifier.")
    public QueueResponseDTO getById(@PathVariable String id) {
        log.info("Fetching queue by ID: {}", id);
        return queueService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update queue", description = "Updates the details of an existing queue.")
    public QueueResponseDTO update(@PathVariable String id, @RequestBody QueueRequestDTO dto) {
        log.info("Updating queue with ID: {}", id);
        return queueService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete queue", description = "Deletes a queue by its ID.")
    public void delete(@PathVariable String id) {
        log.info("Deleting queue with ID: {}", id);
        queueService.delete(id);
    }
}
