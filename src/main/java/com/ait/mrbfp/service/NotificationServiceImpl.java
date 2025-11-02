package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.NotificationRequestDTO;
import com.ait.mrbfp.dto.response.NotificationResponseDTO;
import com.ait.mrbfp.entity.Employee;
import com.ait.mrbfp.entity.Notification;
import com.ait.mrbfp.mapper.NotificationMapper;
import com.ait.mrbfp.repository.EmployeeRepository;
import com.ait.mrbfp.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO dto) {
        log.info("Creating notification for employee: {}", dto.getEmployeeId());

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Notification notification = NotificationMapper.toEntity(dto, employee);
        notificationRepository.save(notification);

        log.info("Notification created successfully with ID: {}", notification.getNotificationId());
        return NotificationMapper.toResponse(notification);
    }

    @Override
    public List<NotificationResponseDTO> getAllNotifications() {
        log.info("Fetching all notifications...");
        return notificationRepository.findAll()
                .stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDTO getNotificationById(String notificationId) {
        log.info("Fetching notification by ID: {}", notificationId);
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return NotificationMapper.toResponse(n);
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByEmployee(String employeeId) {
        log.info("Fetching notifications for employee ID: {}", employeeId);
        return notificationRepository.findByEmployee_EmployeeId(employeeId)
                .stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDTO updateNotification(String notificationId, NotificationRequestDTO dto) {
        log.info("Updating notification ID: {}", notificationId);

        Notification existing = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        NotificationMapper.updateEntity(existing, dto, employee);
        notificationRepository.save(existing);
        return NotificationMapper.toResponse(existing);
    }

    @Override
    public void deleteNotification(String notificationId) {
        log.warn("Deleting notification ID: {}", notificationId);
        notificationRepository.deleteById(notificationId);
    }
}
