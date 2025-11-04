package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.UserLoginRequestDTO;
import com.ait.mrb_fp.dto.response.UserLoginResponseDTO;
import com.ait.mrb_fp.entity.*;

public class UserLoginMapper {

    private UserLoginMapper() {}

    public static UserLogin toEntity(UserLoginRequestDTO r, Employee e) {
        UserLogin u = new UserLogin();
        u.setEmployee(e);
        u.setUsername(r.getUsername());
        u.setPassword(r.getPassword());
        u.setRole(UserLogin.Role.valueOf(r.getRole()));
        u.setActive(true);
        return u;
    }

    public static UserLoginResponseDTO toResponse(UserLogin u) {
        UserLoginResponseDTO r = new UserLoginResponseDTO();
        r.setLoginId(u.getLoginId());
        r.setEmployeeName(u.getEmployee() != null ? u.getEmployee().getFirstName() + " " + u.getEmployee().getLastName() : null);
        r.setUsername(u.getUsername());
        r.setRole(u.getRole().name());
        r.setActive(u.isActive());
        return r;
    }

    public static void updateEntity(UserLogin u, UserLoginRequestDTO r, Employee e) {
        u.setEmployee(e);
        u.setUsername(r.getUsername());
        u.setPassword(r.getPassword());
        u.setRole(UserLogin.Role.valueOf(r.getRole()));
    }
}
