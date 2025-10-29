package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
import com.ait.mrb_fp.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationResponseDTO> getAll() {
        return notificationService.getAll();
    }

    @GetMapping("/{id}")
    public NotificationResponseDTO getById(@PathVariable String id) {
        return notificationService.getById(id);
    }

    @PostMapping
    public NotificationResponseDTO create(@RequestBody NotificationRequestDTO request) {
        return notificationService.create(request);
    }

    @PutMapping("/{id}")
    public NotificationResponseDTO update(@PathVariable String id, @RequestBody NotificationRequestDTO request) {
        return notificationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
