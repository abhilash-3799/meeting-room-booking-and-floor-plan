package com.ait.mrbfp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @Column(length = 50, nullable = false)
    private String seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @Column(length = 50, nullable = false, unique = true)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private SeatStatus seatStatus = SeatStatus.UNALLOCATED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_team_id")
    private Team assignedTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_id")
    private Queue queue;

    @Column(nullable = false)
    private boolean isAvailable = true;

    @Column(nullable = false)
    private boolean active = true;

    public enum SeatStatus {
        UNALLOCATED, ALLOCATED
    }

    @PrePersist
    public void generateId() {
        if (this.seatId == null) {
            this.seatId = "SEAT-" + java.util.UUID.randomUUID();
        }
    }
}
