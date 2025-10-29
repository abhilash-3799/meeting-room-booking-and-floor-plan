package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"room", "bookedBy"})
@EqualsAndHashCode(exclude = {"room", "bookedBy"})
public class MeetingRoomBooking {

    @Id
    @Column(length = 10)
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

    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MeetingStatus status;

    @Column(nullable = false)
    private boolean isActive = true;

    public enum MeetingStatus {
        BOOKED, CANCELLED, COMPLETED
    }
}
