package com.ait.mrbfp.repository;

import com.ait.mrbfp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByEmployeeNumber(String employeeNumber);
    boolean existsByEmail(String email);
}
