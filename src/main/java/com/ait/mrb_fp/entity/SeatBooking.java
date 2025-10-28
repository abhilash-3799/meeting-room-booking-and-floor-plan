package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seat_booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBooking {

    @Id
    @Column(length = 10)
    private String allocationId;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDateTime allocationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private boolean isActive = true;

    public enum BookingStatus {
        ALLOCATED, RELEASED
    }
}
