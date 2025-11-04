package com.ait.mrbfp.dto.response;

import lombok.Data;

@Data
public class NotificationResponseDTO {

    private String notificationId;
    private String employeeName;
    private String title;
    private String message;
    private boolean active;
}
