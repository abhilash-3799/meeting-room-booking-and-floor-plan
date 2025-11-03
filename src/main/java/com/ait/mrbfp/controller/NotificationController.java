package com.ait.mrbfp.controller;

import com.ait.mrbfp.dto.request.NotificationRequestDTO;
import com.ait.mrbfp.dto.response.NotificationResponseDTO;
import com.ait.mrbfp.service.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "APIs for managing employee notifications")
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    @Operation(summary = "Create a new notification")
    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(@Valid @RequestBody NotificationRequestDTO dto) {
        return ResponseEntity.ok(notificationService.createNotification(dto));
    }

    @Operation(summary = "Get all notifications")
    @GetMapping
    public ResponseEntity<List<NotificationResponseDTO>> getAll() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @Operation(summary = "Get a notification by ID")
    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDTO> getById(@PathVariable String notificationId) {
        return ResponseEntity.ok(notificationService.getNotificationById(notificationId));
    }

    @Operation(summary = "Get notifications by employee ID")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<NotificationResponseDTO>> getByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(notificationService.getNotificationsByEmployee(employeeId));
    }

    @Operation(summary = "Update a notification")
    @PutMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDTO> update(
            @PathVariable String notificationId,
            @Valid @RequestBody NotificationRequestDTO dto) {
        return ResponseEntity.ok(notificationService.updateNotification(notificationId, dto));
    }

    @Operation(summary = "Delete a notification")
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> delete(@PathVariable String notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}
