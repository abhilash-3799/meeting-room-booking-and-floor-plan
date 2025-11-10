package com.ait.mrb_fp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private boolean success;
    private String message;
    private String username;
    private String role;
    private String employeeId;
}
