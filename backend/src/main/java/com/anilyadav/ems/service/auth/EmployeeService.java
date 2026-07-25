package com.anilyadav.ems.service.auth;

import com.anilyadav.ems.dto.request.EmployeeRequest;
import com.anilyadav.ems.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);

    EmployeeResponse getEmployeeByCode(String employeeCode);
}