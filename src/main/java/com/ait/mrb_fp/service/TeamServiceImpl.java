package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.TeamRequestDTO;
import com.ait.mrb_fp.dto.response.TeamResponseDTO;
import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.TeamMapper;
import com.ait.mrb_fp.repository.TeamRepository;
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
    public TeamResponseDTO createTeam(TeamRequestDTO dto) {
        log.info("Creating new team: {}", dto.getTeamName());
        try {
            Team team = TeamMapper.toEntity(dto);
            team.setTeamId("TEAM" + System.currentTimeMillis());
            Team saved = teamRepository.save(team);
            log.info("Team created successfully with ID: {}", saved.getTeamId());
            return TeamMapper.toResponse(saved);
        } catch (Exception e) {
            log.error("Error creating team '{}': {}", dto.getTeamName(), e.getMessage());
            throw e;
        }
    }

    @Override
    public TeamResponseDTO getTeamById(String teamId) {
        log.info("Fetching team by ID: {}", teamId);
        try {
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));
            return TeamMapper.toResponse(team);
        } catch (Exception e) {
            log.error("Error fetching team ID {}: {}", teamId, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<TeamResponseDTO> getAllTeams() {
        log.info("Fetching all active teams");
        try {
            return teamRepository.findByIsActiveTrue()
                    .stream()
                    .map(TeamMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching team list: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public TeamResponseDTO updateTeam(String teamId, TeamRequestDTO dto) {
        log.info("Updating team with ID: {}", teamId);
        try {
            Team existing = teamRepository.findById(teamId)
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));

            existing.setTeamName(dto.getTeamName());
            existing.setDepartment(dto.getDepartment());

            Team updated = teamRepository.save(existing);
            log.info("Team with ID {} updated successfully", teamId);
            return TeamMapper.toResponse(updated);
        } catch (Exception e) {
            log.error("Error updating team ID {}: {}", teamId, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deactivateTeam(String teamId) {
        log.info("Deactivating team with ID: {}", teamId);
        try {
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));
            team.setActive(false);
            teamRepository.save(team);
            log.info("Team with ID {} deactivated successfully", teamId);
        } catch (Exception e) {
            log.error("Error deactivating team ID {}: {}", teamId, e.getMessage());
            throw e;
        }
    }
}
