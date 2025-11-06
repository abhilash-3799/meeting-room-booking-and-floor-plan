package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "shift")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shift {

    @Id
    @Column(length = 45, nullable = false)
    private String shiftId;
    @PrePersist
    public void prePersist() {
        if (this.shiftId == null || this.shiftId.isBlank()) {

            this.shiftId = "SHIFT-" + UUID.randomUUID().toString().substring(0, 4);
        }
    }

    @Column(length = 100, nullable = false, unique = true)
    private String shiftName;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private boolean isActive = true;
}

