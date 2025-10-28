package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.LoginRequestDTO;
import com.ait.mrb_fp.dto.LoginResponseDTO;
import com.ait.mrb_fp.entity.UserLogin;
import com.ait.mrb_fp.repository.UserLoginRepository;
import com.ait.mrb_fp.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserLoginRepository userLoginRepository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {

        UserLogin user = userLoginRepository.findByUsername(loginRequest.getUsername())
                .orElse(null);

        if (user == null) {
            return LoginResponseDTO.builder()
                    .success(false)
                    .message("❌ Invalid username")
                    .build();
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return LoginResponseDTO.builder()
                    .success(false)
                    .message("❌ Invalid password")
                    .build();
        }

        if (!user.isActive()) {
            return LoginResponseDTO.builder()
                    .success(false)
                    .message("⚠️ Account inactive. Please contact admin.")
                    .build();
        }

        return LoginResponseDTO.builder()
                .success(true)
                .message("✅ Login successful")
                .username(user.getUsername())
                .role(user.getRole().name())
                .employeeId(user.getEmployee().getEmployeeId())
                .build();
    }
}
