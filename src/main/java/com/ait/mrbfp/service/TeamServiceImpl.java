package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.TeamRequestDTO;
import com.ait.mrbfp.dto.response.TeamResponseDTO;
import com.ait.mrbfp.entity.Team;
import com.ait.mrbfp.mapper.TeamMapper;
import com.ait.mrbfp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public TeamResponseDTO createTeam(TeamRequestDTO requestDTO) {
        log.info("Creating team: {}", requestDTO.getTeamName());
        if (teamRepository.existsByTeamName(requestDTO.getTeamName())) {
            log.error("Duplicate team name: {}", requestDTO.getTeamName());
            throw new RuntimeException("Team name already exists: " + requestDTO.getTeamName());
        }
        Team team = TeamMapper.toEntity(requestDTO);
        team = teamRepository.save(team);
        log.info("Team created with ID: {}", team.getTeamId());
        return TeamMapper.toResponse(team);
    }

    @Override
    public List<TeamResponseDTO> getAllTeams() {
        log.info("Fetching all teams...");
        return teamRepository.findAll()
                .stream()
                .map(TeamMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TeamResponseDTO getTeamById(String teamId) {
        log.info("Fetching team with ID: {}", teamId);
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + teamId));
        return TeamMapper.toResponse(team);
    }

    @Override
    public TeamResponseDTO updateTeam(String teamId, TeamRequestDTO requestDTO) {
        log.info("Updating team with ID: {}", teamId);
        Team existing = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + teamId));
        if (!existing.getTeamName().equals(requestDTO.getTeamName())
                && teamRepository.existsByTeamName(requestDTO.getTeamName())) {
            log.error("Duplicate team name during update: {}", requestDTO.getTeamName());
            throw new RuntimeException("Team name already exists: " + requestDTO.getTeamName());
        }
        TeamMapper.updateEntity(existing, requestDTO);
        teamRepository.save(existing);
        log.info("Team updated successfully: {}", existing.getTeamId());
        return TeamMapper.toResponse(existing);
    }

    @Override
    public void deleteTeam(String teamId) {
        log.warn("Deleting team with ID: {}", teamId);
        teamRepository.deleteById(teamId);
    }
}
