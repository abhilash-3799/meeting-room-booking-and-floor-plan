package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @Column(length = 10)
    private String employeeId;

    @Column(length = 10, unique = true, nullable = false)
    private String employeeNumber;

    @Column(length = 100, nullable = false)
    private String firstName;

    @Column(length = 100, nullable = false)
    private String lastName;

    @Column(length = 150, nullable = false)
    private String email;

    // ✅ Many employees belong to one team
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    // ✅ Many employees belong to one office
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeType employeeType;

    @Column(nullable = false)
    private boolean isTeamLead;

    @Column(nullable = false)
    private boolean isActive = true;

    public enum EmployeeType {
        REGULAR, HYBRID, WFH
    }
}
