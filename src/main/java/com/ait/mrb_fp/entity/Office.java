package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "office")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"employees", "meetingRooms", "queues", "seats"})
@EqualsAndHashCode(exclude = {"employees", "meetingRooms", "queues", "seats"})
public class Office {

    @Id
    @Column(length = 10)
    private String officeId;

    @Column(length = 100, nullable = false)
    private String officeName;

    @Column(length = 200, nullable = false)
    private String location;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingRoom> meetingRooms;

    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Queue> queues;

    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;
}
