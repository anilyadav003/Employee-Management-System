package com.anilyadav.ems.service.department;

import com.anilyadav.ems.dto.request.DepartmentRequest;
import com.anilyadav.ems.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    DepartmentResponse getDepartmentById(Long id);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse updateDepartment(Long id, DepartmentRequest request);

    void deleteDepartment(Long id);

    DepartmentResponse getDepartmentByCode(String code);
}