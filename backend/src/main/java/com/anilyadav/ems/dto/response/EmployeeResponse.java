package com.anilyadav.ems.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class EmployeeResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String employeeCode;

    private LocalDate dateOfJoining;

    private String designation;

    private Double salary;

    private Long departmentId;

    private String departmentName;

    private Long userId;

    private String username;
}