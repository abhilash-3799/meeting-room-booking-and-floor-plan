package com.ait.mrbfp.repository;

import com.ait.mrbfp.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, String> {

    Optional<UserLogin> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmployee_EmployeeId(String employeeId);
}
