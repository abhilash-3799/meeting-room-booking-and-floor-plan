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
public class Office {

    @Id
    @Column(length = 10)
    private String officeId; // Custom ID like "OFC001"

    @Column(length = 100, nullable = false)
    private String officeName;

    @Column(length = 200, nullable = false)
    private String location;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Employee> employees;
}
