package com.ait.mrbfp.service;


import com.ait.mrbfp.dto.request.EmployeeRequestDTO;
import com.ait.mrbfp.dto.response.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);

    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO getEmployeeById(String employeeId);

    EmployeeResponseDTO updateEmployee(String employeeId, EmployeeRequestDTO requestDTO);

    void deleteEmployee(String employeeId);
}
