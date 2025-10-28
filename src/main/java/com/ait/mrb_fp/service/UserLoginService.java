package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.LoginRequestDTO;
import com.ait.mrb_fp.dto.response.LoginResponseDTO;

public interface UserLoginService {
    LoginResponseDTO login(LoginRequestDTO loginRequest);
}
