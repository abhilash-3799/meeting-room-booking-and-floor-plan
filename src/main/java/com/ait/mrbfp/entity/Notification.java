package com.ait.mrbfp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @Column(length = 50, nullable = false)
    private String notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(length = 150, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean active = true;

    @PrePersist
    public void generateId() {
        if (this.notificationId == null) {
            this.notificationId = "NOTI-" + java.util.UUID.randomUUID();
        }
    }
}
