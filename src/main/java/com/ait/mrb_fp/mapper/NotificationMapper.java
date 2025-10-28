package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.NotificationDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.Notification;

public class NotificationMapper {

    public static NotificationDTO toDTO(Notification notification) {
        if (notification == null) return null;

        return NotificationDTO.builder()
                .notificationId(notification.getNotificationId())
                .employeeId(notification.getEmployee() != null ? notification.getEmployee().getEmployeeId() : null)
                .employeeName(notification.getEmployee() != null
                        ? notification.getEmployee().getFirstName() + " " + notification.getEmployee().getLastName()
                        : null)
                .title(notification.getTitle())
                .message(notification.getMessage())
                .isActive(notification.isActive())
                .build();
    }

    public static Notification toEntity(NotificationDTO dto) {
        if (dto == null) return null;

        Employee employee = dto.getEmployeeId() != null
                ? Employee.builder().employeeId(dto.getEmployeeId()).build()
                : null;

        return Notification.builder()
                .notificationId(dto.getNotificationId())
                .employee(employee)
                .title(dto.getTitle())
                .message(dto.getMessage())
                .isActive(dto.isActive())
                .build();
    }
}
