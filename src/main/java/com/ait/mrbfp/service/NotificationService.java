package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.NotificationRequestDTO;
import com.ait.mrbfp.dto.response.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(NotificationRequestDTO dto);

    List<NotificationResponseDTO> getAllNotifications();

    NotificationResponseDTO getNotificationById(String notificationId);

    List<NotificationResponseDTO> getNotificationsByEmployee(String employeeId);

    NotificationResponseDTO updateNotification(String notificationId, NotificationRequestDTO dto);

    void deleteNotification(String notificationId);
}
