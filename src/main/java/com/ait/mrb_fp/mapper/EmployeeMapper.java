package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.EmployeeDTO;
import com.ait.mrb_fp.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee employee) {
        if (employee == null) return null;
        return EmployeeDTO.builder()
                .employeeId(employee.getEmployeeId())
                .employeeNumber(employee.getEmployeeNumber())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .teamId(employee.getTeamId())
                .officeId(employee.getOfficeId())
                .employeeType(employee.getEmployeeType().name())
                .isTeamLead(employee.isTeamLead())
                .isActive(employee.isActive())
                .build();
    }

    public static Employee toEntity(EmployeeDTO dto) {
        if (dto == null) return null;
        return Employee.builder()
                .employeeId(dto.getEmployeeId())
                .employeeNumber(dto.getEmployeeNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .teamId(dto.getTeamId())
                .officeId(dto.getOfficeId())
                .employeeType(Employee.EmployeeType.valueOf(dto.getEmployeeType()))
                .isTeamLead(dto.isTeamLead())
                .isActive(dto.isActive())
                .build();
    }
}
