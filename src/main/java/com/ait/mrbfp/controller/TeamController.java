package com.ait.mrbfp.controller;

import com.ait.mrbfp.dto.request.TeamRequestDTO;
import com.ait.mrbfp.dto.response.TeamResponseDTO;
import com.ait.mrbfp.service.TeamServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@Tag(name = "Team Management", description = "APIs for managing teams")
public class TeamController {

    private final TeamServiceImpl teamService;

    @Operation(summary = "Create a new team")
    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@Valid @RequestBody TeamRequestDTO requestDTO) {
        return ResponseEntity.ok(teamService.createTeam(requestDTO));
    }

    @Operation(summary = "Get all teams")
    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @Operation(summary = "Get team by ID")
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable String teamId) {
        return ResponseEntity.ok(teamService.getTeamById(teamId));
    }

    @Operation(summary = "Update team details")
    @PutMapping("/{teamId}")
    public ResponseEntity<TeamResponseDTO> updateTeam(
            @PathVariable String teamId,
            @Valid @RequestBody TeamRequestDTO requestDTO) {
        return ResponseEntity.ok(teamService.updateTeam(teamId, requestDTO));
    }

    @Operation(summary = "Delete team by ID")
    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }
}
