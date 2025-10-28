package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.TeamDTO;
import com.ait.mrb_fp.entity.Team;

public class TeamMapper {

    public static TeamDTO toDTO(Team team) {
        if (team == null) return null;
        return TeamDTO.builder()
                .teamId(team.getTeamId())
                .teamName(team.getTeamName())
                .department(team.getDepartment())
                .isActive(team.isActive())
                .build();
    }

    public static Team toEntity(TeamDTO dto) {
        if (dto == null) return null;
        return Team.builder()
                .teamId(dto.getTeamId())
                .teamName(dto.getTeamName())
                .department(dto.getDepartment())
                .isActive(dto.isActive())
                .build();
    }
}

