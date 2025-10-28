package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.UserLoginRequestDTO;
import com.ait.mrb_fp.dto.response.UserLoginResponseDTO;
import com.ait.mrb_fp.entity.UserLogin;
import com.ait.mrb_fp.entity.Employee;

public class UserLoginMapper {

    public static UserLogin toEntity(UserLoginRequestDTO dto, Employee employee) {
        return UserLogin.builder()
                .employee(employee)
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(UserLogin.Role.valueOf(dto.getRole()))
                .isActive(true)
                .build();
    }

    public static UserLoginResponseDTO toResponse(UserLogin entity) {
        return UserLoginResponseDTO.builder()
                .loginId(entity.getLoginId())
                .employeeName(entity.getEmployee().getFirstName() + " " + entity.getEmployee().getLastName())
                .username(entity.getUsername())
                .role(entity.getRole().name())
                .isActive(entity.isActive())
                .build();
    }
}
