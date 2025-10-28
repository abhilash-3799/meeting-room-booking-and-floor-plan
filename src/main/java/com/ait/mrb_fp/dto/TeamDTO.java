package com.ait.mrb_fp.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDTO {
    private String teamId;
    private String teamName;
    private String department;
    private boolean isActive;
    private List<EmployeeDTO> employees;
}
