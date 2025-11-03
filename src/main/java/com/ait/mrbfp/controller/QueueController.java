package com.ait.mrbfp.controller;

import com.ait.mrbfp.dto.request.QueueRequestDTO;
import com.ait.mrbfp.dto.response.QueueResponseDTO;
import com.ait.mrbfp.service.QueueServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queues")
@RequiredArgsConstructor
@Tag(name = "Queue", description = "APIs for managing office queues")
public class QueueController {

    private final QueueServiceImpl queueService;

    @Operation(summary = "Create a new queue")
    @PostMapping
    public ResponseEntity<QueueResponseDTO> create(@Valid @RequestBody QueueRequestDTO dto) {
        return ResponseEntity.ok(queueService.createQueue(dto));
    }

    @Operation(summary = "Get all queues")
    @GetMapping
    public ResponseEntity<List<QueueResponseDTO>> getAll() {
        return ResponseEntity.ok(queueService.getAllQueues());
    }

    @Operation(summary = "Get queue by ID")
    @GetMapping("/{queueId}")
    public ResponseEntity<QueueResponseDTO> getById(@PathVariable String queueId) {
        return ResponseEntity.ok(queueService.getQueueById(queueId));
    }

    @Operation(summary = "Get queues by office ID")
    @GetMapping("/office/{officeId}")
    public ResponseEntity<List<QueueResponseDTO>> getByOffice(@PathVariable String officeId) {
        return ResponseEntity.ok(queueService.getQueuesByOffice(officeId));
    }

    @Operation(summary = "Update a queue")
    @PutMapping("/{queueId}")
    public ResponseEntity<QueueResponseDTO> update(
            @PathVariable String queueId,
            @Valid @RequestBody QueueRequestDTO dto) {
        return ResponseEntity.ok(queueService.updateQueue(queueId, dto));
    }

    @Operation(summary = "Delete a queue")
    @DeleteMapping("/{queueId}")
    public ResponseEntity<Void> delete(@PathVariable String queueId) {
        queueService.deleteQueue(queueId);
        return ResponseEntity.noContent().build();
    }
}
