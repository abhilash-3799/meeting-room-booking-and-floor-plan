package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "meeting_room")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoom {

    @Id
    @Column(length = 10)
    private String roomId;

    @ManyToOne
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @Column(length = 100, nullable = false)
    private String roomName;

    @Column(nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<MeetingBooking> bookings;

    public enum RoomType {
        BOARD_ROOM, CONFERENCE_ROOM, CABIN
    }
}
