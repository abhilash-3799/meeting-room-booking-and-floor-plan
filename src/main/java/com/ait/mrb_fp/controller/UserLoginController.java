package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.LoginRequestDTO;
import com.ait.mrb_fp.dto.LoginResponseDTO;
import com.ait.mrb_fp.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = userLoginService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
