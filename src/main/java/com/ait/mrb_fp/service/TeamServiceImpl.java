package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.TeamRequestDTO;
import com.ait.mrb_fp.dto.response.TeamResponseDTO;
import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.TeamMapper;
import com.ait.mrb_fp.repository.TeamRepository;
import com.ait.mrb_fp.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public TeamResponseDTO createTeam(TeamRequestDTO dto) {
        Team team = TeamMapper.toEntity(dto);
        team.setTeamId("TEAM" + System.currentTimeMillis()); // simple ID generator
        return TeamMapper.toResponse(teamRepository.save(team));
    }

    @Override
    public TeamResponseDTO getTeamById(String teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));
        return TeamMapper.toResponse(team);
    }

    @Override
    public List<TeamResponseDTO> getAllTeams() {
        return teamRepository.findByIsActiveTrue()
                .stream()
                .map(TeamMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TeamResponseDTO updateTeam(String teamId, TeamRequestDTO dto) {
        Team existing = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));

        existing.setTeamName(dto.getTeamName());
        existing.setDepartment(dto.getDepartment());

        return TeamMapper.toResponse(teamRepository.save(existing));
    }

    @Override
    public void deactivateTeam(String teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));
        team.setActive(false);
        teamRepository.save(team);
    }
}
