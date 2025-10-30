package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.EmployeeRequestDTO;
import com.ait.mrb_fp.dto.response.EmployeeResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.entity.Shift;

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
        e.setEmployeeType(Employee.EmployeeType.valueOf(request.getEmployeeType()));

        e.setActive(true);
        return e;
    }

    public static EmployeeResponseDTO toResponse(Employee e) {
        EmployeeResponseDTO r = new EmployeeResponseDTO();
        r.setEmployeeId(e.getEmployeeId());
        r.setEmployeeNumber(e.getEmployeeNumber());
        r.setFirstName(e.getFirstName());
        r.setLastName(e.getLastName());
        r.setEmail(e.getEmail());
        r.setTeamName(e.getTeam() != null ? e.getTeam().getTeamName() : null);
        r.setOfficeName(e.getOffice() != null ? e.getOffice().getOfficeName() : null);
        r.setShiftName(e.getShift() != null ? e.getShift().getShiftName() : null);
        r.setEmployeeType(e.getEmployeeType().name());

        r.setActive(e.isActive());
        return r;
    }

    public static void updateEntity(Employee existing, EmployeeRequestDTO request, Team team, Office office, Shift shift) {
        existing.setEmployeeNumber(request.getEmployeeNumber());
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());
        existing.setTeam(team);
        existing.setOffice(office);
        existing.setShift(shift);
        existing.setEmployeeType(Employee.EmployeeType.valueOf(request.getEmployeeType()));

    }
}
