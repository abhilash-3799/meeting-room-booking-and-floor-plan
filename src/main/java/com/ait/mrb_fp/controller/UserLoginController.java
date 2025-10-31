package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.LoginRequestDTO;
import com.ait.mrb_fp.dto.response.LoginResponseDTO;
import com.ait.mrb_fp.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        log.info("Login request received for username: {}", loginRequest.getUsername());
        LoginResponseDTO response = userLoginService.login(loginRequest);
        log.info("User {} logged in successfully", loginRequest.getUsername());
        return ResponseEntity.ok(response);
    }
}
