package com.ait.mrb_fp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginResponseDTO {
    private String loginId;
    private String employeeName;
    private String username;
    private String role;
    private boolean isActive;
}
