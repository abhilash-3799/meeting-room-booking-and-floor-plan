package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.UserLoginRequestDTO;
import com.ait.mrbfp.dto.response.UserLoginResponseDTO;

import java.util.List;

public interface UserLoginService {

    UserLoginResponseDTO createLogin(UserLoginRequestDTO dto);

    List<UserLoginResponseDTO> getAllLogins();

    UserLoginResponseDTO getLoginById(String loginId);

    UserLoginResponseDTO authenticate(String username, String password);

    void deactivateUser(String loginId);
}
