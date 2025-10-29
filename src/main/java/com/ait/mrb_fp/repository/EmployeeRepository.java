package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findByIsActiveTrue();
}
