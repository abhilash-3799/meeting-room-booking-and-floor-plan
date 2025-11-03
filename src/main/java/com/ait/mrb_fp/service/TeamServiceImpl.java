package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.TeamRequestDTO;
import com.ait.mrb_fp.dto.response.TeamResponseDTO;
import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.exception.*;
import com.ait.mrb_fp.mapper.TeamMapper;
import com.ait.mrb_fp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public TeamResponseDTO createTeam(TeamRequestDTO dto) {
        log.info("Attempting to create team: {}", dto != null ? dto.getTeamName() : "null");
        try {
            if (dto == null)
                throw new InvalidRequestBodyException("Team request body cannot be null.");

            if (dto.getTeamName() == null || dto.getTeamName().isBlank())
                throw new MissingRequestParameterException("Team name is required.");

            boolean exists = teamRepository.existsByTeamName(dto.getTeamName());
            if (exists)
                throw new DuplicateResourceException("Team already exists: " + dto.getTeamName());

            Team team = TeamMapper.toEntity(dto);
            team.setTeamId("TEAM" + System.currentTimeMillis());
            team.setActive(true);

            Team saved = teamRepository.save(team);
            log.info("Team created successfully with ID: {}", saved.getTeamId());
            return TeamMapper.toResponse(saved);

        } catch (DataAccessException ex) {
            log.error("Database error while creating team: {}", ex.getMessage());
            throw new DatabaseException("Database error occurred while creating team.");
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed while creating team: {}", ex.getMessage());
            throw new TransactionFailedException("Transaction failed while creating team.");
        } catch (RuntimeException ex) {
            log.error("Unexpected error creating team: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public TeamResponseDTO getTeamById(String teamId) {
        log.info("Fetching team by ID: {}", teamId);
        try {
            if (teamId == null || teamId.isBlank())
                throw new MissingRequestParameterException("Team ID cannot be empty.");

            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new TeamNotFoundException("Team not found with ID: " + teamId));

            if (!team.isActive())
                throw new InvalidStateException("Team is inactive. Reactivate before accessing.");

            return TeamMapper.toResponse(team);
        } catch (TeamNotFoundException | InvalidStateException ex) {
            log.error("Error fetching team {}: {}", teamId, ex.getMessage());
            throw ex;
        } catch (RuntimeException ex) {
            log.error("Unexpected error fetching team {}: {}", teamId, ex.getMessage());
            throw ex;
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
        } catch (DataAccessException ex) {
            log.error("Database error while fetching teams: {}", ex.getMessage());
            throw new DatabaseException("Database error occurred while fetching team list.");
        } catch (RuntimeException ex) {
            log.error("Unexpected error fetching team list: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public TeamResponseDTO updateTeam(String teamId, TeamRequestDTO dto) {
        log.info("Updating team with ID: {}", teamId);
        try {
            if (teamId == null || teamId.isBlank())
                throw new MissingRequestParameterException("Team ID is required for update.");
            if (dto == null)
                throw new InvalidRequestBodyException("Team update request cannot be null.");

            Team existing = teamRepository.findById(teamId)
                    .orElseThrow(() -> new TeamNotFoundException("Team not found with ID: " + teamId));

            if (!existing.isActive())
                throw new InvalidStateException("Cannot update an inactive team.");

            Team duplicate = teamRepository.findByTeamName(dto.getTeamName());
            if (duplicate != null && !duplicate.getTeamId().equals(teamId))
                throw new DuplicateResourceException("Another team already exists with name: " + dto.getTeamName());

            existing.setTeamName(dto.getTeamName());
            existing.setDepartment(dto.getDepartment());

            Team saved = teamRepository.save(existing);
            log.info("Team updated successfully: {}", saved.getTeamId());
            return TeamMapper.toResponse(saved);

        } catch (DataAccessException ex) {
            log.error("Database error while updating team {}: {}", teamId, ex.getMessage());
            throw new DatabaseException("Error occurred while updating team.");
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed during team update {}: {}", teamId, ex.getMessage());
            throw new TransactionFailedException("Transaction failed during team update.");
        } catch (RuntimeException ex) {
            log.error("Unexpected error updating team {}: {}", teamId, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void deactivateTeam(String teamId) {
        log.warn("Deactivating team with ID: {}", teamId);
        try {
            if (teamId == null || teamId.isBlank())
                throw new MissingRequestParameterException("Team ID is required for deactivation.");

            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new TeamNotFoundException("Team not found with ID: " + teamId));

            if (!team.isActive())
                throw new InvalidStateException("Team is already inactive.");

            team.setActive(false);
            teamRepository.save(team);
            log.info("Team deactivated successfully: {}", teamId);

        } catch (DataAccessException ex) {
            log.error("Database error while deactivating team {}: {}", teamId, ex.getMessage());
            throw new DatabaseException("Error updating team status in database.");
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed while deactivating team {}: {}", teamId, ex.getMessage());
            throw new TransactionFailedException("Transaction failed while deactivating team.");
        } catch (RuntimeException ex) {
            log.error("Unexpected error while deactivating team {}: {}", teamId, ex.getMessage());
            throw ex;
        }
    }
}
