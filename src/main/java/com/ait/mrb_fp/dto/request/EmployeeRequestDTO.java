package com.ait.mrb_fp.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDTO {

    @NotNull(message = "employeeNumber is required")
    private String employeeNumber;

    @NotNull(message = "First name is required")
    @Size(max = 26 , message = "First name must not exceed 26 characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(max = 26 , message = "Last name must not exceed 26 characters")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Team ID is required")
    private String teamId;

    @NotBlank(message = "Office ID is required")
    private String officeId;

    @NotBlank(message = "Shift ID is required")
    private String shiftId;

    @NotBlank(message = "Employee type must be 'Full-Time', 'Part-Time', or 'Contract'")
    private String employeeType;

    @NotNull(message = "isTeamLead flag must be specified")
    private boolean isTeamLead;
}
