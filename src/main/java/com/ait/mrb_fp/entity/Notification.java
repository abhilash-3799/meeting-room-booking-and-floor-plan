package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @Column(length = 10)
    private String notificationId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(length = 150, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean isActive = true;
}
