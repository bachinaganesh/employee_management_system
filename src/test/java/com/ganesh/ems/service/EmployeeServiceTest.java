package com.ganesh.ems.service;

import com.ganesh.ems.dtos.request.EmployeeRequestDTO;
import com.ganesh.ems.dtos.response.EmployeeResponseDTO;
import com.ganesh.ems.enums.Gender;
import com.ganesh.ems.models.Employee;
import com.ganesh.ems.repository.EmployeeRepository;
import com.ganesh.ems.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .name("Ganesh")
                .email("ganesh@gmail.com")
                .gender(Gender.MALE)
                .build();
    }

    @Test
    void testCreateEmployee_WhenEmployeeRequestDTOValid_ShouldReturnCreatedEmployee() {
//        Assign
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

//        Act
        EmployeeResponseDTO responseDTO = employeeService.createEmployee(new EmployeeRequestDTO("Ganesh", "ganesh@gmail.com", Gender.MALE));


//        Assert
        ArgumentCaptor<Employee>  captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        Employee capturedEmployee = captor.getValue();

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getName()).isEqualTo(capturedEmployee.getName());
        Assertions.assertThat(responseDTO.getEmail()).isEqualTo(capturedEmployee.getEmail());
        verify(employeeRepository, atMost(2)).save(any(Employee.class));
    }

    @Test
    void testGetEmployeeById_WhenEmployeeExists_ShouldReturnEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponseDTO employeeResponseDTO = employeeService.getEmployeeById(1L);
        Assertions.assertThat(employeeResponseDTO).isNotNull();

    }

    @Test
    void testGetEmployeeById_WhenEmployeeDoesNotExist_ShouldThrowError() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> employeeService.getEmployeeById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee not found with id 1");
    }

    @Test
    void testDeleteEmployeeById_WhenEmployeeExists_ShouldDeleteEmployee() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        String response = employeeService.deleteEmployeeById(1L);

        Assertions.assertThat(response).isEqualTo("Employee with id " + 1L + " has been deleted");
    }

    @Test
    void testDeleteEmployeeById_WhenEmployeeDoesNotExist_ShouldThrowError() {
        when(employeeRepository.existsById(1L)).thenReturn(false);
        Assertions.assertThatThrownBy(() -> employeeService.deleteEmployeeById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee not found with id " + 1L);
    }
}
