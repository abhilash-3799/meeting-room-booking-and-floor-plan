package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "team")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @Column(length = 10)
    private String teamId;

    @Column(length = 100, nullable = false)
    private String teamName;

    @Column(length = 100, nullable = false)
    private String department;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Employee> employees;
}
