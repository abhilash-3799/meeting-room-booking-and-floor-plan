package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.LoginRequestDTO;
import com.ait.mrb_fp.dto.response.LoginResponseDTO;
import com.ait.mrb_fp.service.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Login Controller", description = "API for user authentication and login management")
public class UserLoginController {

    private final UserLoginService userLoginService;

    @Operation(summary = "Authenticate user login", description = "Validates username and password, returns user details if successful")
    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        log.info("Login attempt for username: {}", loginRequest.getUsername());
        try {
            LoginResponseDTO response = userLoginService.login(loginRequest);
            log.info("Login result for user {}: {}", loginRequest.getUsername(), response.getMessage());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Error during login for user {}: {}", loginRequest.getUsername(), ex.getMessage());
            throw ex;
        }
    }
}
