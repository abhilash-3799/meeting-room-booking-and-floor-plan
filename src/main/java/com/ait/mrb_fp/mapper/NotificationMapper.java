package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.NotificationDTO;
import com.ait.mrb_fp.entity.Notification;

public class NotificationMapper {

    public static NotificationDTO toDTO(Notification notification) {
        if (notification == null) return null;
        return NotificationDTO.builder()
                .notificationId(notification.getNotificationId())
                .employeeId(notification.getEmployeeId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .isActive(notification.isActive())
                .build();
    }

    public static Notification toEntity(NotificationDTO dto) {
        if (dto == null) return null;
        return Notification.builder()
                .notificationId(dto.getNotificationId())
                .employeeId(dto.getEmployeeId())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .isActive(dto.isActive())
                .build();
    }
}
