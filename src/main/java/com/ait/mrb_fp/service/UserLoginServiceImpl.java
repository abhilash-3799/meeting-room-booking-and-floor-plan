package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.LoginRequestDTO;
import com.ait.mrb_fp.dto.response.LoginResponseDTO;
import com.ait.mrb_fp.entity.UserLogin;
import com.ait.mrb_fp.repository.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserLoginRepository userLoginRepository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        log.info("Attempting login for username: {}", loginRequest.getUsername());
        try {
            UserLogin user = userLoginRepository.findByUsername(loginRequest.getUsername())
                    .orElse(null);

            if (user == null) {
                log.warn("Login failed: Invalid username '{}'", loginRequest.getUsername());
                return LoginResponseDTO.builder()
                        .success(false)
                        .message("Invalid username")
                        .build();
            }

            if (!user.getPassword().equals(loginRequest.getPassword())) {
                log.warn("Login failed for username '{}': Invalid password", loginRequest.getUsername());
                return LoginResponseDTO.builder()
                        .success(false)
                        .message("Invalid password")
                        .build();
            }

            if (!user.isActive()) {
                log.warn("Login attempt for inactive account: {}", loginRequest.getUsername());
                return LoginResponseDTO.builder()
                        .success(false)
                        .message("Account inactive. Please contact admin.")
                        .build();
            }

            log.info("User '{}' logged in successfully", loginRequest.getUsername());
            return LoginResponseDTO.builder()
                    .success(true)
                    .message("Login successful")
                    .username(user.getUsername())
                    .role(user.getRole().name())
                    .employeeId(user.getEmployee().getEmployeeId())
                    .build();
        } catch (Exception e) {
            log.error("Error during login for username '{}': {}", loginRequest.getUsername(), e.getMessage());
            throw e;
        }
    }
}
