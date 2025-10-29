package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.TeamRequestDTO;
import com.ait.mrb_fp.dto.response.TeamResponseDTO;
import com.ait.mrb_fp.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamRequestDTO dto) {
        return ResponseEntity.ok(teamService.createTeam(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable String id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> updateTeam(@PathVariable String id, @RequestBody TeamRequestDTO dto) {
        return ResponseEntity.ok(teamService.updateTeam(id, dto));
    }

    // ✅ Soft Delete (sets isActive = false)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateTeam(@PathVariable String id) {
        teamService.deactivateTeam(id);
        return ResponseEntity.ok("Team marked as inactive (soft deleted).");
    }
}
