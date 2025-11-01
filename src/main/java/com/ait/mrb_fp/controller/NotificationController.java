package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
import com.ait.mrb_fp.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
@Slf4j
@Tag(
        name = "Notification Management",
        description = "APIs for creating, retrieving, updating, and deleting system notifications."
)
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    @Operation(
            summary = "Get all notifications",
            description = "Retrieves a list of all notifications in the system."
    )
    public List<NotificationResponseDTO> getAll() {
        log.info("Fetching all notifications");
        return notificationService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get notification by ID",
            description = "Fetches a specific notification using its unique ID."
    )
    public NotificationResponseDTO getById(@PathVariable String id) {
        log.info("Fetching notification ID: {}", id);
        return notificationService.getById(id);
    }

    @PostMapping
    @Operation(
            summary = "Create a new notification",
            description = "Adds a new notification entry to the system."
    )
    public NotificationResponseDTO create(@RequestBody NotificationRequestDTO request) {
        log.info("Creating notification: {}", request);
        return notificationService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a notification",
            description = "Updates details of an existing notification by its ID."
    )
    public NotificationResponseDTO update(@PathVariable String id, @RequestBody NotificationRequestDTO request) {
        log.info("Updating notification ID: {}", id);
        return notificationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a notification",
            description = "Removes a notification from the system by its ID."
    )
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting notification ID: {}", id);
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
