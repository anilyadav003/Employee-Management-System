package com.anilyadav.ems.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    private String lastName;

    @NotBlank(message = "Employee code is required")
    @Size(max = 15)
    private String employeeCode;

    @NotNull(message = "Date of joining is required")
    private LocalDate dateOfJoining;

    @NotBlank(message = "Designation is required")
    @Size(max = 100)
    private String designation;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than zero")
    private Double salary;

    @NotNull(message = "Department ID is required")
    @Min(value = 1, message = "Department ID must be valid")
    private Long departmentId;

    @NotNull(message = "User ID is required")
    @Min(value = 1, message = "User ID must be valid")
    private Long userId;
}