package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDateTime;
import java.util.UUID;

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
    @Column(length = 45, nullable = false)
    private String allocationId;
    @PrePersist
    public void generateId() {
        if (this.allocationId == null || this.allocationId.isBlank()) {

            this.allocationId= "SB-" + UUID.randomUUID().toString().substring(0, 4);
        }
    }

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

    @Column(nullable = false)
    private boolean isActive = true;

    public enum BookingStatus {
        ALLOCATED, RELEASED
    }
}


