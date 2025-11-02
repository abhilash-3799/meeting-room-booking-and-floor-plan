package com.ait.mrbfp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TeamResponseDTO {

    private String teamId;

    private String teamName;

    private String department;

    private boolean active;
}
