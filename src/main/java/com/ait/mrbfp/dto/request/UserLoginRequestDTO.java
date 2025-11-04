package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDTO {

    @NotBlank
    private String employeeId;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String role;
}
