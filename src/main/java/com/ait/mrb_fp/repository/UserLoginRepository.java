package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin, String> {
    Optional<UserLogin> findByUsername(String username);
}
