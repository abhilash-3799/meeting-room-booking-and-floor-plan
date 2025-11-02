package com.ait.mrbfp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "queue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Queue {

    @Id
    @Column(length = 50, nullable = false)
    private String queueId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @Column(length = 50, nullable = false, unique = true)
    private String queueName;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "queue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    @PrePersist
    public void generateId() {
        if (this.queueId == null) {
            this.queueId = "QUEUE-" + java.util.UUID.randomUUID();
        }
    }
}
