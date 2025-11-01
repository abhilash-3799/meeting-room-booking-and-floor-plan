package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.LoginRequestDTO;
import com.ait.mrb_fp.dto.response.LoginResponseDTO;
import com.ait.mrb_fp.service.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@Tag(name = "User Authentication", description = "Endpoints for user login and authentication.")
public class UserLoginController {

    private final UserLoginService userLoginService;

    @Operation(summary = "Authenticate user and generate login response")
    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        log.info("Login attempt for username: {}", loginRequest.getUsername());
        LoginResponseDTO response = userLoginService.login(loginRequest);
        log.info("Login result for user {}: {}", loginRequest.getUsername(), response.isSuccess());
        return ResponseEntity.ok(response);
    }
}
