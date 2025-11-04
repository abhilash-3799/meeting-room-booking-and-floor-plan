package com.ait.mrbfp.mapper;

import com.ait.mrbfp.dto.request.UserLoginRequestDTO;
import com.ait.mrbfp.dto.response.UserLoginResponseDTO;
import com.ait.mrbfp.entity.Employee;
import com.ait.mrbfp.entity.UserLogin;
import com.ait.mrbfp.exception.BadRequestException;

public class UserLoginMapper {

    private UserLoginMapper() {}

    public static UserLogin toEntity(UserLoginRequestDTO dto, Employee employee) {
        UserLogin user = new UserLogin();
        user.setEmployee(employee);
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        try {
            user.setRole(UserLogin.Role.valueOf(dto.getRole().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid role: " + dto.getRole());
        }
        user.setActive(true);
        return user;
    }

    public static UserLoginResponseDTO toResponse(UserLogin user) {
        UserLoginResponseDTO dto = new UserLoginResponseDTO();
        dto.setLoginId(user.getLoginId());
        dto.setEmployeeId(user.getEmployee().getEmployeeId());
        dto.setEmployeeName(user.getEmployee().getFirstName() + " " + user.getEmployee().getLastName());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole().name());
        dto.setActive(user.isActive());
        return dto;
    }
}
