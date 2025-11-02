package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamRequestDTO {

    @NotBlank
    private String teamName;

    private String department;

    private boolean active = true;
}
