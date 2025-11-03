package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamRequestDTO {

    @Schema(description = "Team name", example = "Development Team")
    @NotBlank
    private String teamName;

    @Schema(description = "Department name", example = "Engineering")
    private String department;

    @Schema(description = "Whether the team is active", example = "true")
    private boolean active = true;
}
