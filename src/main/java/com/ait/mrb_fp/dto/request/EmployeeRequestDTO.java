package com.ait.mrb_fp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDTO {

    @NotBlank(message = "Employee Number is required.")
    private String employeeNumber;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Invalid email format")
    @Email
    private String email;

    @NotBlank(message = "Team Id is required")
    private String teamId;

    @NotBlank(message = "Office Id is required")
    private String officeId;

    @NotBlank(message = "Employee type is required")
    private String employeeType;

    @NotBlank()
    private boolean teamLead;
}
