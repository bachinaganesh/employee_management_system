package com.ganesh.ems.repository;

import com.ganesh.ems.config.EmployeeTestConfiguration;
import com.ganesh.ems.enums.Gender;
import com.ganesh.ems.models.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;


@DataJpaTest
@Import(EmployeeTestConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .name("Ganesh")
                .email("ganesh@gmail.com")
                .gender(Gender.MALE)
                .build();
    }

    @Test
    public void testCreateEmployee_ShouldReturnEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isNotNull();

    }

    @Test
    public void testFindById_ShouldReturnEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        Optional<Employee> optionalEmployee = employeeRepository.findById(savedEmployee.getId());
        Assertions.assertThat(optionalEmployee.isPresent()).isTrue();
    }

    @Test
    public void testFindByEmail_ShouldReturnEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(savedEmployee.getEmail());
        Assertions.assertThat(optionalEmployee.isPresent()).isTrue();
    }

    @Test
    public void testFindByEmail_ShouldReturnEmptyOptional() {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail("ganesh@gmail.com");
        Assertions.assertThat(optionalEmployee.isPresent()).isFalse();
    }
}
