package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.UserLoginDTO;
import com.ait.mrb_fp.entity.UserLogin;

public class UserLoginMapper {

    public static UserLoginDTO toDTO(UserLogin login) {
        if (login == null) return null;
        return UserLoginDTO.builder()
                .loginId(login.getLoginId())
                .employeeId(login.getEmployeeId())
                .username(login.getUsername())
                .role(login.getRole().name())
                .isActive(login.isActive())
                .build();
    }

    public static UserLogin toEntity(UserLoginDTO dto) {
        if (dto == null) return null;
        return UserLogin.builder()
                .loginId(dto.getLoginId())
                .employeeId(dto.getEmployeeId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(UserLogin.Role.valueOf(dto.getRole()))
                .isActive(dto.isActive())
                .build();
    }
}
