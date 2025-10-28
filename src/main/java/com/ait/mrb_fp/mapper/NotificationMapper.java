package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
import com.ait.mrb_fp.entity.Notification;
import com.ait.mrb_fp.entity.Employee;

public class NotificationMapper {

    public static Notification toEntity(NotificationRequestDTO dto, Employee employee) {
        return Notification.builder()
                .employee(employee)
                .title(dto.getTitle())
                .message(dto.getMessage())
                .isActive(true)
                .build();
    }

    public static NotificationResponseDTO toResponse(Notification entity) {
        return NotificationResponseDTO.builder()
                .notificationId(entity.getNotificationId())
                .employeeName(entity.getEmployee().getFirstName() + " " + entity.getEmployee().getLastName())
                .title(entity.getTitle())
                .message(entity.getMessage())
                .isActive(entity.isActive())
                .build();
    }
}
