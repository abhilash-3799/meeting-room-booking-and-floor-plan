package com.ait.mrbfp.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeResponseDTO {

    private String employeeId;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String teamName;
    private String officeName;
    private String shiftName;
    private String employeeType;
    private boolean teamLead;
    private boolean active;
}
