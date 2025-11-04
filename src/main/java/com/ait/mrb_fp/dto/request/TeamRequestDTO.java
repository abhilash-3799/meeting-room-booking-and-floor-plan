package com.ait.mrb_fp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRequestDTO {
    private String teamName;
    private String department;
}
