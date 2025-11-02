package com.ait.mrbfp.mapper;

import com.ait.mrbfp.dto.request.NotificationRequestDTO;
import com.ait.mrbfp.dto.response.NotificationResponseDTO;
import com.ait.mrbfp.entity.Employee;
import com.ait.mrbfp.entity.Notification;

public class NotificationMapper {

    private NotificationMapper() {}

    public static Notification toEntity(NotificationRequestDTO dto, Employee employee) {
        Notification n = new Notification();
        n.setEmployee(employee);
        n.setTitle(dto.getTitle());
        n.setMessage(dto.getMessage());
        n.setActive(true);
        return n;
    }

    public static NotificationResponseDTO toResponse(Notification n) {
        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setNotificationId(n.getNotificationId());
        dto.setEmployeeName(n.getEmployee() != null
                ? n.getEmployee().getFirstName() + " " + n.getEmployee().getLastName()
                : null);
        dto.setTitle(n.getTitle());
        dto.setMessage(n.getMessage());
        dto.setActive(n.isActive());
        return dto;
    }

    public static void updateEntity(Notification existing, NotificationRequestDTO dto, Employee employee) {
        existing.setEmployee(employee);
        existing.setTitle(dto.getTitle());
        existing.setMessage(dto.getMessage());
    }
}
