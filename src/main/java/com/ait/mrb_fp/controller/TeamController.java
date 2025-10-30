package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.TeamRequestDTO;
import com.ait.mrb_fp.dto.response.TeamResponseDTO;
import com.ait.mrb_fp.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamRequestDTO dto) {
        log.info("Creating new team: {}", dto);
        TeamResponseDTO response = teamService.createTeam(dto);
        log.info("Team created successfully with ID: {}", response.getTeamId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable String id) {
        log.info("Fetching team by ID: {}", id);
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        log.info("Fetching all teams");
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> updateTeam(@PathVariable String id, @RequestBody TeamRequestDTO dto) {
        log.info("Updating team ID: {}", id);
        return ResponseEntity.ok(teamService.updateTeam(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateTeam(@PathVariable String id) {
        log.info("Deactivating team ID: {}", id);
        teamService.deactivateTeam(id);
        log.info("Team {} marked as inactive", id);
        return ResponseEntity.ok("Team marked as inactive (soft deleted).");
    }
}
