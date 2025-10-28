package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @Column(length = 10)
    private String seatId;

    @ManyToOne
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @Column(length = 50, nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus seatStatus = SeatStatus.UNALLOCATED;

    @ManyToOne
    @JoinColumn(name = "assigned_team_id")
    private Team assignedTeam;

    @ManyToOne
    @JoinColumn(name = "queue_id")
    private Queue queue;

    @Column(nullable = false)
    private boolean isAvailable;

    @Column(nullable = false)
    private boolean isActive = true;

    public enum SeatStatus {
        UNALLOCATED, ALLOCATED
    }
}
