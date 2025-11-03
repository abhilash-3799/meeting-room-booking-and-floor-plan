package com.ait.mrbfp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TeamResponseDTO {

    @Schema(description = "Unique ID of the team", example = "TEAM-4b7f38c5-8db8-49a0-84cb-9b6f87e1e64b")
    private String teamId;

    @Schema(description = "Name of the team", example = "Development Team")
    private String teamName;

    @Schema(description = "Department name", example = "Engineering")
    private String department;

    @Schema(description = "Indicates whether the team is active", example = "true")
    private boolean active;
}
