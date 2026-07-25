package com.anilyadav.ems.service.impl.department;

import com.anilyadav.ems.dto.request.DepartmentRequest;
import com.anilyadav.ems.dto.response.DepartmentResponse;
import com.anilyadav.ems.entity.department.Department;
import com.anilyadav.ems.exception.ResourceAlreadyExistsException;
import com.anilyadav.ems.repository.DepartmentRepository;
import com.anilyadav.ems.service.auth.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.anilyadav.ems.exception.ResourceNotFoundException;

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

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id : " + id));

        return mapToDepartmentResponse(department);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::mapToDepartmentResponse)
                .toList();
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id : " + id));

        if (!department.getName().equals(request.getName())
                && departmentRepository.existsByName(request.getName())) {

            throw new ResourceAlreadyExistsException(
                    "Department already exists with name : " + request.getName());
        }

        if (!department.getCode().equals(request.getCode())
                && departmentRepository.existsByCode(request.getCode())) {

            throw new ResourceAlreadyExistsException(
                    "Department already exists with code : " + request.getCode());
        }

        department.setName(request.getName());
        department.setCode(request.getCode());
        department.setDescription(request.getDescription());

        Department updatedDepartment = departmentRepository.save(department);

        return mapToDepartmentResponse(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id : " + id));

        departmentRepository.delete(department);
    }

    @Override
    public DepartmentResponse getDepartmentByCode(String code) {

        Department department = departmentRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with code : " + code));

        return mapToDepartmentResponse(department);
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