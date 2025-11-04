package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.LoginRequestDTO;
import com.ait.mrb_fp.dto.response.LoginResponseDTO;
import com.ait.mrb_fp.entity.UserLogin;
import com.ait.mrb_fp.repository.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

    private final UserLoginRepository userLoginRepository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        log.info("Processing login for username: {}", loginRequest.getUsername());
        try {
            UserLogin user = userLoginRepository.findByUsername(loginRequest.getUsername())
                    .orElse(null);

            if (user == null) {
                log.warn("Login failed — username not found: {}", loginRequest.getUsername());
                return LoginResponseDTO.builder()
                        .success(false)
                        .message("Invalid username")
                        .build();
            }

            if (!user.getPassword().equals(loginRequest.getPassword())) {
                log.warn("Login failed — invalid password for user: {}", loginRequest.getUsername());
                return LoginResponseDTO.builder()
                        .success(false)
                        .message("Invalid password")
                        .build();
            }

            if (!user.isActive()) {
                log.warn("Login failed — inactive account for user: {}", loginRequest.getUsername());
                return LoginResponseDTO.builder()
                        .success(false)
                        .message("Account inactive. Please contact admin.")
                        .build();
            }

            log.info("User {} logged in successfully.", user.getUsername());
            return LoginResponseDTO.builder()
                    .success(true)
                    .message("Login successful")
                    .username(user.getUsername())
                    .role(user.getRole().name())
                    .employeeId(user.getEmployee().getEmployeeId())
                    .build();

        } catch (Exception ex) {
            log.error("Error during login for user {}: {}", loginRequest.getUsername(), ex.getMessage());
            throw ex;
        }
    }
}
