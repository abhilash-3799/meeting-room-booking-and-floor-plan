package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
import com.ait.mrb_fp.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationResponseDTO> getAll() {
        log.info("Fetching all notifications");
        return notificationService.getAll();
    }

    @GetMapping("/{id}")
    public NotificationResponseDTO getById(@PathVariable String id) {
        log.info("Fetching notification by ID: {}", id);
        return notificationService.getById(id);
    }

    @PostMapping
    public NotificationResponseDTO create(@RequestBody NotificationRequestDTO request) {
        log.info("Creating new notification: {}", request);
        return notificationService.create(request);
    }

    @PutMapping("/{id}")
    public NotificationResponseDTO update(@PathVariable String id, @RequestBody NotificationRequestDTO request) {
        log.info("Updating notification ID: {}", id);
        return notificationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting notification ID: {}", id);
        notificationService.delete(id);
        log.info("Notification {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}
