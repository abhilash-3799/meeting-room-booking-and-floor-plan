package com.ait.mrbfp.controller;

import com.ait.mrbfp.dto.request.UserLoginRequestDTO;
import com.ait.mrbfp.dto.response.UserLoginResponseDTO;
import com.ait.mrbfp.service.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-logins")
@RequiredArgsConstructor
@Tag(name = "User Login", description = "APIs for user authentication and management")
public class UserLoginController {

    private final UserLoginService userLoginService;

    @Operation(summary = "Create a new user login")
    @PostMapping
    public ResponseEntity<UserLoginResponseDTO> create(@Valid @RequestBody UserLoginRequestDTO dto) {
        return ResponseEntity.ok(userLoginService.createLogin(dto));
    }

    @Operation(summary = "Get all user logins")
    @GetMapping
    public ResponseEntity<List<UserLoginResponseDTO>> getAll() {
        return ResponseEntity.ok(userLoginService.getAllLogins());
    }

    @Operation(summary = "Get login by ID")
    @GetMapping("/{loginId}")
    public ResponseEntity<UserLoginResponseDTO> getById(@PathVariable String loginId) {
        return ResponseEntity.ok(userLoginService.getLoginById(loginId));
    }

    @Operation(summary = "Authenticate a user (basic check)")
    @PostMapping("/authenticate")
    public ResponseEntity<UserLoginResponseDTO> authenticate(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(userLoginService.authenticate(username, password));
    }

    @Operation(summary = "Deactivate a user account")
    @PutMapping("/{loginId}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable String loginId) {
        userLoginService.deactivateUser(loginId);
        return ResponseEntity.noContent().build();
    }
}
