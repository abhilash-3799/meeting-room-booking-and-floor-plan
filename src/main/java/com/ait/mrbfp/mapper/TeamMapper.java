package com.ait.mrbfp.mapper;

import com.ait.mrbfp.dto.request.TeamRequestDTO;
import com.ait.mrbfp.dto.response.TeamResponseDTO;
import com.ait.mrbfp.entity.Team;

public class TeamMapper {

    private TeamMapper() {}

    public static Team toEntity(TeamRequestDTO dto) {
        Team team = new Team();
        team.setTeamName(dto.getTeamName());
        team.setDepartment(dto.getDepartment());
        team.setActive(dto.isActive());
        return team;
    }

    public static TeamResponseDTO toResponse(Team team) {
        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setTeamId(team.getTeamId());
        dto.setTeamName(team.getTeamName());
        dto.setDepartment(team.getDepartment());
        dto.setActive(team.isActive());
        return dto;
    }

    public static void updateEntity(Team existing, TeamRequestDTO dto) {
        existing.setTeamName(dto.getTeamName());
        existing.setDepartment(dto.getDepartment());
        existing.setActive(dto.isActive());
    }
}
