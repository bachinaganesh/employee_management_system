package com.ganesh.ems.service.impl;

import com.ganesh.ems.dtos.request.EmployeeRequestDTO;
import com.ganesh.ems.dtos.response.EmployeeResponseDTO;
import com.ganesh.ems.exceptions.ResourceNotFoundException;
import com.ganesh.ems.models.Employee;
import com.ganesh.ems.repository.EmployeeRepository;
import com.ganesh.ems.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = this.modelMapper.map(employeeRequestDTO, Employee.class);
        Employee savedEmployee = this.employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeResponseDTO.class);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }
}
