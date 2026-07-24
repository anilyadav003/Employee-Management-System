package com.anilyadav.ems.service.impl.department;

import com.anilyadav.ems.dto.request.DepartmentRequest;
import com.anilyadav.ems.dto.response.DepartmentResponse;
import com.anilyadav.ems.entity.department.Department;
import com.anilyadav.ems.exception.ResourceAlreadyExistsException;
import com.anilyadav.ems.repository.DepartmentRepository;
import com.anilyadav.ems.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        if (departmentRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException(
                    "Department already exists with name : " + request.getName());
        }

        if (departmentRepository.existsByCode(request.getCode())) {
            throw new ResourceAlreadyExistsException(
                    "Department already exists with code : " + request.getCode());
        }

        Department department = Department.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .build();

        Department savedDepartment = departmentRepository.save(department);

        return mapToDepartmentResponse(savedDepartment);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        return null;
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return List.of();
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {
        return null;
    }

    @Override
    public void deleteDepartment(Long id) {

    }

    @Override
    public DepartmentResponse getDepartmentByCode(String code) {
        return null;
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {

        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .description(department.getDescription())
                .build();
    }
}