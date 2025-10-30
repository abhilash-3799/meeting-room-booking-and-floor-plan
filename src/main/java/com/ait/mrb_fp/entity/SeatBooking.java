package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDateTime;

@Entity
@Table(name = "seat_booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"seat", "employee"})
@EqualsAndHashCode(exclude = {"seat", "employee"})
public class SeatBooking {

    @Id
    @Column(length = 36, nullable = false)
    private String allocationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDateTime allocationDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private BookingStatus status = BookingStatus.ALLOCATED;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;

    public enum BookingStatus {
        ALLOCATED, RELEASED
    }
}


