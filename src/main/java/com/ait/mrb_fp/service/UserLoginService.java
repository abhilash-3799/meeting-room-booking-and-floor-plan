package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.LoginRequestDTO;
import com.ait.mrb_fp.dto.LoginResponseDTO;

public interface UserLoginService {
    LoginResponseDTO login(LoginRequestDTO loginRequest);
}
