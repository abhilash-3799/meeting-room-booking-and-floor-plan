package com.ait.mrbfp.mapper;

import com.ait.mrbfp.dto.request.EmployeeRequestDTO;
import com.ait.mrbfp.dto.response.EmployeeResponseDTO;
import com.ait.mrbfp.entity.Employee;
import com.ait.mrbfp.entity.Office;
import com.ait.mrbfp.entity.Shift;
import com.ait.mrbfp.entity.Team;
import com.ait.mrbfp.exception.BadRequestException;

public class EmployeeMapper {

    private EmployeeMapper() {}

    public static Employee toEntity(EmployeeRequestDTO request, Team team, Office office, Shift shift) {
        Employee e = new Employee();
        e.setEmployeeNumber(request.getEmployeeNumber());
        e.setFirstName(request.getFirstName());
        e.setLastName(request.getLastName());
        e.setEmail(request.getEmail());
        e.setTeam(team);
        e.setOffice(office);
        e.setShift(shift);

        try {
            e.setEmployeeType(Employee.EmployeeType.valueOf(request.getEmployeeType().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid employee type: " + request.getEmployeeType());
        }

        e.setTeamLead(request.isTeamLead());
        e.setActive(true);
        return e;
    }

    public static EmployeeResponseDTO toResponse(Employee e) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setEmployeeId(e.getEmployeeId());
        dto.setEmployeeNumber(e.getEmployeeNumber());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setEmail(e.getEmail());
        dto.setTeamName(e.getTeam() != null ? e.getTeam().getTeamName() : null);
        dto.setOfficeName(e.getOffice() != null ? e.getOffice().getOfficeName() : null);
        dto.setShiftName(e.getShift() != null ? e.getShift().getShiftName() : null);
        dto.setEmployeeType(e.getEmployeeType().name());
        dto.setTeamLead(e.isTeamLead());
        dto.setActive(e.isActive());
        return dto;
    }

    public static void updateEntity(Employee existing, EmployeeRequestDTO request, Team team, Office office, Shift shift) {
        existing.setEmployeeNumber(request.getEmployeeNumber());
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());
        existing.setTeam(team);
        existing.setOffice(office);
        existing.setShift(shift);

        try {
            existing.setEmployeeType(Employee.EmployeeType.valueOf(request.getEmployeeType().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid employee type: " + request.getEmployeeType());
        }

        existing.setTeamLead(request.isTeamLead());
    }
}
