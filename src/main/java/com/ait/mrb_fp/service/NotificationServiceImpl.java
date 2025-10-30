package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
import com.ait.mrb_fp.entity.Notification;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.mapper.NotificationMapper;
import com.ait.mrb_fp.repository.NotificationRepository;
import com.ait.mrb_fp.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   EmployeeRepository employeeRepository) {
        this.notificationRepository = notificationRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<NotificationResponseDTO> getAll() {
        log.info("Fetching all notifications");
        try {
            return notificationRepository.findAll()
                    .stream()
                    .map(NotificationMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching notifications: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public NotificationResponseDTO getById(String id) {
        log.info("Fetching notification by ID: {}", id);
        try {
            Notification notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found: " + id));
            return NotificationMapper.toResponse(notification);
        } catch (Exception e) {
            log.error("Error fetching notification ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public NotificationResponseDTO create(NotificationRequestDTO request) {
        log.info("Creating notification for employee ID: {}", request.getEmployeeId());
        try {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getEmployeeId()));

            Notification notification = NotificationMapper.toEntity(request, employee);
            notificationRepository.save(notification);
            log.info("Notification created successfully with ID: {}", notification.getNotificationId());
            return NotificationMapper.toResponse(notification);
        } catch (Exception e) {
            log.error("Error creating notification: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public NotificationResponseDTO update(String id, NotificationRequestDTO request) {
        log.info("Updating notification with ID: {}", id);
        try {
            Notification existing = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found: " + id));

            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getEmployeeId()));

            NotificationMapper.updateEntity(existing, request, employee);
            notificationRepository.save(existing);
            log.info("Notification with ID {} updated successfully", id);
            return NotificationMapper.toResponse(existing);
        } catch (Exception e) {
            log.error("Error updating notification ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(String id) {
        log.info("Deleting notification with ID: {}", id);
        try {
            notificationRepository.deleteById(id);
            log.info("Notification with ID {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting notification ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
