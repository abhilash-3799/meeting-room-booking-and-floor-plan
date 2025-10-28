package com.ait.mrb_fp.dto;

import lombok.*;
import com.ait.mrb_fp.entity.Employee.EmployeeType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    private String employeeId;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String email;

    private String teamId;
    private String teamName;

    private String officeId;
    private String officeName;

    private EmployeeType employeeType;
    private boolean isTeamLead;
    private boolean isActive;
}
