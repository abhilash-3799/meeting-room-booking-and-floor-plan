package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shift")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shift {

    @Id
    @Column(length = 10)
    private String shiftId;

    @Column(length = 100, nullable = false)
    private String shiftName;

    @Column(nullable = false)
    private java.time.LocalTime startTime;

    @Column(nullable = false)
    private java.time.LocalTime endTime;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private boolean isActive = true;
}
