package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
