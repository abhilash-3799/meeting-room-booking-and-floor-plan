package com.ait.mrb_fp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDTO {
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String teamId;
    private String officeId;
    private String shiftId;
    private String employeeType;
    private boolean isTeamLead;
}
