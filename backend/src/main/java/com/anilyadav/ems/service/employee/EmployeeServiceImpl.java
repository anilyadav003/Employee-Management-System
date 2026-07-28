package com.anilyadav.ems.service.employee;

import com.anilyadav.ems.dto.request.EmployeeRequest;
import com.anilyadav.ems.dto.response.EmployeeResponse;
import com.anilyadav.ems.entity.auth.User;
import com.anilyadav.ems.entity.department.Department;
import com.anilyadav.ems.entity.employee.Employee;
import com.anilyadav.ems.exception.ResourceAlreadyExistsException;
import com.anilyadav.ems.exception.ResourceNotFoundException;
import com.anilyadav.ems.repository.DepartmentRepository;
import com.anilyadav.ems.repository.EmployeeRepository;
import com.anilyadav.ems.repository.UserRepository;
import com.anilyadav.ems.service.auth.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        if (employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new ResourceAlreadyExistsException(
                    "Employee already exists with code : " + request.getEmployeeCode());
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id : " + request.getDepartmentId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id : " + request.getUserId()));

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .employeeCode(request.getEmployeeCode())
                .dateOfJoining(request.getDateOfJoining())
                .designation(request.getDesignation())
                .salary(request.getSalary())
                .department(department)
                .user(user)
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        return mapToEmployeeResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id : " + id));

        return mapToEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::mapToEmployeeResponse)
                .toList();
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id : " + id));

        if (!employee.getEmployeeCode().equals(request.getEmployeeCode())
                && employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {

            throw new ResourceAlreadyExistsException(
                    "Employee already exists with code : " + request.getEmployeeCode());
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id : " + request.getDepartmentId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id : " + request.getUserId()));

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setDateOfJoining(request.getDateOfJoining());
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
        employee.setDepartment(department);
        employee.setUser(user);

        Employee updatedEmployee = employeeRepository.save(employee);

        return mapToEmployeeResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id : " + id));

        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeResponse getEmployeeByCode(String employeeCode) {

        Employee employee = employeeRepository.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with code : " + employeeCode));

        return mapToEmployeeResponse(employee);
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {

        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .employeeCode(employee.getEmployeeCode())
                .dateOfJoining(employee.getDateOfJoining())
                .designation(employee.getDesignation())
                .salary(employee.getSalary())
                .departmentId(employee.getDepartment().getId())
                .departmentName(employee.getDepartment().getName())
                .userId(employee.getUser().getId())
                .username(employee.getUser().getUsername())
                .build();
    }
}