package com.ait.mrb_fp.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private String notificationId;
    private String employeeId;
    private String employeeName;
    private String title;
    private String message;
    private boolean isActive;
}
