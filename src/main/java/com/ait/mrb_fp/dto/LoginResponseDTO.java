package com.ait.mrb_fp.dto;

import lombok.*;

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
