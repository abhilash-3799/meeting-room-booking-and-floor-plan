package com.ait.mrbfp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoomBooking {

    @Id
    @Column(length = 50, nullable = false)
    private String meetingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private MeetingRoom room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booked_by_employee_id", nullable = false)
    private Employee bookedBy;

    @Column(nullable = false)
    private LocalDate meetingDate;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(length = 255)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private MeetingStatus status = MeetingStatus.BOOKED;

    @Column(nullable = false)
    private boolean active = true;

    public enum MeetingStatus {
        BOOKED, CANCELLED, COMPLETED
    }

    @PrePersist
    public void generateId() {
        if (this.meetingId == null) {
            this.meetingId = "MRB-" + java.util.UUID.randomUUID();
        }
    }
}
