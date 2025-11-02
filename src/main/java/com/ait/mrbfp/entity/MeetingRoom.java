package com.ait.mrbfp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "meeting_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoom {

    @Id
    @Column(length = 50, nullable = false)
    private String roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @Column(length = 100, nullable = false, unique = true)
    private String roomName;

    @Column(nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MeetingRoomStatus roomStatus = MeetingRoomStatus.AVAILABLE;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingRoomBooking> bookings;

    public enum RoomType {
        BOARD_ROOM, CONFERENCE_ROOM, CABIN
    }

    public enum MeetingRoomStatus {
        AVAILABLE, NOT_AVAILABLE
    }

    @PrePersist
    public void generateId() {
        if (this.roomId == null) {
            this.roomId = "MEETINGROOM-" + java.util.UUID.randomUUID();
        }
    }
}
