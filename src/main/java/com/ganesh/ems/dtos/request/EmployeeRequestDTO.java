package com.ganesh.ems.dtos.request;

import com.ganesh.ems.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @Email
    @NotBlank(message = "Email is required")
    private String email;
    @NotNull(message = "Gender is required")
    private Gender gender;
}
