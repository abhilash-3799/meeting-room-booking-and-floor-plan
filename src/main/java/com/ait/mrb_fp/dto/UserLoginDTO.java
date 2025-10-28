package com.ait.mrb_fp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDTO {
    private String loginId;
    private String employeeId;
    private String username;
    private String password; // For request only — omit from responses
    private String role;
    private boolean isActive;
}
