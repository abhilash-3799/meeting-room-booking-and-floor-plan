package com.ait.mrb_fp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRequestDTO {

    @NotBlank(message = "Team name required")
    private String teamName;

    @NotBlank(message = "Department Required")
    private String department;
}
