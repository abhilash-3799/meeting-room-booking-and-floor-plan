package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.TeamRequestDTO;
import com.ait.mrb_fp.dto.response.TeamResponseDTO;
import com.ait.mrb_fp.entity.Team;

public class TeamMapper {

    public static Team toEntity(TeamRequestDTO dto) {
        return Team.builder()
                .teamName(dto.getTeamName())
                .department(dto.getDepartment())
                .isActive(true)
                .build();
    }

    public static TeamResponseDTO toResponse(Team entity) {
        return TeamResponseDTO.builder()
                .teamId(entity.getTeamId())
                .teamName(entity.getTeamName())
                .department(entity.getDepartment())
                .isActive(entity.isActive())
                .build();
    }
}
