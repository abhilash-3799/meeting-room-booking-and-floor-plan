package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.UserLoginRequestDTO;
import com.ait.mrbfp.dto.response.UserLoginResponseDTO;
import com.ait.mrbfp.entity.Employee;
import com.ait.mrbfp.entity.UserLogin;
import com.ait.mrbfp.mapper.UserLoginMapper;
import com.ait.mrbfp.repository.EmployeeRepository;
import com.ait.mrbfp.repository.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserLoginServiceImpl implements UserLoginService {

    private final UserLoginRepository userLoginRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserLoginResponseDTO createLogin(UserLoginRequestDTO dto) {
        log.info("Creating user login for employee: {}", dto.getEmployeeId());

        if (userLoginRepository.existsByUsername(dto.getUsername())) {
            log.error("Username already exists: {}", dto.getUsername());
            throw new RuntimeException("Username already exists: " + dto.getUsername());
        }

        if (userLoginRepository.existsByEmployee_EmployeeId(dto.getEmployeeId())) {
            log.error("Login already exists for employee: {}", dto.getEmployeeId());
            throw new RuntimeException("Login already exists for this employee.");
        }

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + dto.getEmployeeId()));

        UserLogin user = UserLoginMapper.toEntity(dto, employee);
        user = userLoginRepository.save(user);

        return UserLoginMapper.toResponse(user);
    }

    @Override
    public List<UserLoginResponseDTO> getAllLogins() {
        return userLoginRepository.findAll()
                .stream()
                .map(UserLoginMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserLoginResponseDTO getLoginById(String loginId) {
        UserLogin user = userLoginRepository.findById(loginId)
                .orElseThrow(() -> new RuntimeException("Login not found: " + loginId));
        return UserLoginMapper.toResponse(user);
    }

    @Override
    public UserLoginResponseDTO authenticate(String username, String password) {
        UserLogin user = userLoginRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(password)) {
            log.error("Invalid login attempt for username: {}", username);
            throw new RuntimeException("Invalid username or password");
        }

        if (!user.isActive()) {
            log.error("Inactive account login attempt: {}", username);
            throw new RuntimeException("Account is deactivated");
        }

        return UserLoginMapper.toResponse(user);
    }

    @Override
    public void deactivateUser(String loginId) {
        UserLogin user = userLoginRepository.findById(loginId)
                .orElseThrow(() -> new RuntimeException("Login not found: " + loginId));
        user.setActive(false);
        userLoginRepository.save(user);
        log.info("User {} deactivated successfully", loginId);
    }
}
