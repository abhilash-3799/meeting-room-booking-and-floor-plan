package com.ait.mrbfp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Table(name = "shift")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shift {

    @Id
    @Column(length = 50, nullable = false)
    private String shiftId;

    @Column(length = 100, nullable = false, unique = true)
    private String shiftName;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @PrePersist
    public void generateId() {
        if (this.shiftId == null) {
            this.shiftId = "SHIFT-" + java.util.UUID.randomUUID();
        }
    }
}
