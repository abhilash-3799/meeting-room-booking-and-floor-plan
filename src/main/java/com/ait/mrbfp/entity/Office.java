package com.ait.mrbfp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "office")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Office {

    @Id
    @Column(length = 50, nullable = false)
    private String officeId;

    @Column(length = 100, nullable = false, unique = true)
    private String officeName;

    @Column(length = 200, nullable = false)
    private String location;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

    @PrePersist
    public void generateId() {
        if (this.officeId == null) {
            this.officeId = "OFFICE-" + java.util.UUID.randomUUID();
        }
    }
}
