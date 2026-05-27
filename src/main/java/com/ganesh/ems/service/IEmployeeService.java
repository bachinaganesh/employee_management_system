package com.ganesh.ems.service;

import com.ganesh.ems.dtos.request.EmployeeRequestDTO;
import com.ganesh.ems.dtos.response.EmployeeResponseDTO;

public interface IEmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO getEmployeeById(Long id);
}
