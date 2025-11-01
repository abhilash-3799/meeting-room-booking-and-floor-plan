package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.TeamRequestDTO;
import com.ait.mrb_fp.dto.response.TeamResponseDTO;
import com.ait.mrb_fp.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
@Tag(name = "Team Management", description = "Endpoints for managing teams.")
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "Create new team")
    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamRequestDTO dto) {
        log.info("Creating new team: {}", dto);
        return ResponseEntity.ok(teamService.createTeam(dto));
    }

    @Operation(summary = "Get team by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable String id) {
        log.info("Fetching team by ID: {}", id);
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @Operation(summary = "Get all teams")
    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        log.info("Fetching all teams");
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @Operation(summary = "Update team details")
    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> updateTeam(@PathVariable String id, @RequestBody TeamRequestDTO dto) {
        log.info("Updating team with ID: {}", id);
        return ResponseEntity.ok(teamService.updateTeam(id, dto));
    }

    @Operation(summary = "Deactivate team")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateTeam(@PathVariable String id) {
        log.info("Deactivating team with ID: {}", id);
        teamService.deactivateTeam(id);
        return ResponseEntity.ok("Team marked as inactive (soft deleted).");
    }
}
