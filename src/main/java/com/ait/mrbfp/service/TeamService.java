package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.TeamRequestDTO;
import com.ait.mrbfp.dto.response.TeamResponseDTO;
import java.util.List;

public interface TeamService {

    TeamResponseDTO createTeam(TeamRequestDTO requestDTO);

    List<TeamResponseDTO> getAllTeams();

    TeamResponseDTO getTeamById(String teamId);

    TeamResponseDTO updateTeam(String teamId, TeamRequestDTO requestDTO);

    void deleteTeam(String teamId);
}
