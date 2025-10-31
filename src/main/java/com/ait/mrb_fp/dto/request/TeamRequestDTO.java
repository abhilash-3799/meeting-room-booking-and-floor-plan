package com.ait.mrb_fp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRequestDTO {

    @NotNull(message = "Team  name is required")
    private String teamName;

    @NotNull(message = "Department is required")
    private String department;
}
