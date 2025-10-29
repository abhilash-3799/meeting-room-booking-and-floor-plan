package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
import com.ait.mrb_fp.entity.Notification;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.mapper.NotificationMapper;
import com.ait.mrb_fp.repository.NotificationRepository;
import com.ait.mrb_fp.repository.EmployeeRepository;
import com.ait.mrb_fp.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return notificationRepository.findAll()
                .stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDTO getById(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + id));
        return NotificationMapper.toResponse(notification);
    }

    @Override
    public NotificationResponseDTO create(NotificationRequestDTO request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getEmployeeId()));

        Notification notification = NotificationMapper.toEntity(request, employee);
        notificationRepository.save(notification);

        return NotificationMapper.toResponse(notification);
    }

    @Override
    public NotificationResponseDTO update(String id, NotificationRequestDTO request) {
        Notification existing = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + id));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getEmployeeId()));

        NotificationMapper.updateEntity(existing, request, employee);
        notificationRepository.save(existing);

        return NotificationMapper.toResponse(existing);
    }

    @Override
    public void delete(String id) {
        notificationRepository.deleteById(id);
    }
}
