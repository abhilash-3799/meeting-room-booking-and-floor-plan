package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_login")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLogin {

    @Id
    @Column(length = 10)
    private String loginId;

    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Column(length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false)
    private boolean isActive = true;

    public enum Role {
        EMPLOYEE, ADMIN, TEAM_LEAD
    }
}
