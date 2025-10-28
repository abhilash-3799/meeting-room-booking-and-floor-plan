package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "queue")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Queue {

    @Id
    @Column(length = 10)
    private String queueId;

    @ManyToOne
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @Column(length = 50, nullable = false)
    private String queueName;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "queue", cascade = CascadeType.ALL)
    private List<Seat> seats;
}
