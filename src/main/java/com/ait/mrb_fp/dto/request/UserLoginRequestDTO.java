package com.ait.mrb_fp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequestDTO {

    private String employeeId;
    private String username;
    private String password;
    private String role;
}
