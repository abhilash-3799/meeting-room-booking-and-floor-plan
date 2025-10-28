package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.EmployeeRequestDTO;
import com.ait.mrb_fp.dto.response.EmployeeResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.entity.Shift;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeRequestDTO dto, Team team, Office office, Shift shift) {
        return Employee.builder()
                .employeeNumber(dto.getEmployeeNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .team(team)
                .office(office)
                .shift(shift)
                .employeeType(Employee.EmployeeType.valueOf(dto.getEmployeeType()))
                .isTeamLead(dto.isTeamLead())
                .isActive(true)
                .build();
    }

    public static EmployeeResponseDTO toResponse(Employee entity) {
        return EmployeeResponseDTO.builder()
                .employeeId(entity.getEmployeeId())
                .employeeNumber(entity.getEmployeeNumber())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .teamName(entity.getTeam() != null ? entity.getTeam().getTeamName() : null)
                .officeName(entity.getOffice() != null ? entity.getOffice().getOfficeName() : null)
                .shiftName(entity.getShift() != null ? entity.getShift().getShiftName() : null)
                .employeeType(entity.getEmployeeType().name())
                .isTeamLead(entity.isTeamLead())
                .isActive(entity.isActive())
                .build();
    }
}
