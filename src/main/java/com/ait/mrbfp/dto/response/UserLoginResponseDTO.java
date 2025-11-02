package com.ait.mrbfp.dto.response;

import lombok.Data;

@Data
public class UserLoginResponseDTO {
    private String loginId;
    private String employeeId;
    private String employeeName;
    private String username;
    private String role;
    private boolean active;
}
