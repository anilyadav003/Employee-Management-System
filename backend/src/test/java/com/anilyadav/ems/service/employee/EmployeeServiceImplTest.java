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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeRequest request;
    private Department department;
    private User user;
    private Employee employee;

    @BeforeEach
    void setUp() {

        request = new EmployeeRequest();
        request.setFirstName("Anil");
        request.setLastName("Yadav");
        request.setEmployeeCode("EMP001");
        request.setDateOfJoining(LocalDate.of(2026, 1, 1));
        request.setDesignation("Software Engineer");
        request.setSalary(50000.0);
        request.setDepartmentId(1L);
        request.setUserId(1L);

        department = Department.builder()
                .name("IT")
                .code("IT001")
                .description("Information Technology")
                .build();
        department.setId(1L);

        user = new User();
        user.setId(1L);
        user.setUsername("anil");

        employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .employeeCode(request.getEmployeeCode())
                .dateOfJoining(request.getDateOfJoining())
                .designation(request.getDesignation())
                .salary(request.getSalary())
                .department(department)
                .user(user)
                .build();

        employee.setId(1L);
    }

    @Test
    void createEmployee_ShouldCreateEmployeeSuccessfully() {

        when(employeeRepository.existsByEmployeeCode("EMP001"))
                .thenReturn(false);

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        EmployeeResponse response =
                employeeService.createEmployee(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Anil", response.getFirstName());
        assertEquals("Yadav", response.getLastName());
        assertEquals("EMP001", response.getEmployeeCode());
        assertEquals("Software Engineer", response.getDesignation());
        assertEquals(50000.0, response.getSalary());
        assertEquals("IT", response.getDepartmentName());
        assertEquals("anil", response.getUsername());

        verify(employeeRepository).existsByEmployeeCode("EMP001");
        verify(departmentRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void createEmployee_ShouldThrowException_WhenEmployeeCodeAlreadyExists() {

        when(employeeRepository.existsByEmployeeCode("EMP001"))
                .thenReturn(true);

        ResourceAlreadyExistsException exception =
                assertThrows(
                        ResourceAlreadyExistsException.class,
                        () -> employeeService.createEmployee(request)
                );

        assertEquals(
                "Employee already exists with code : EMP001",
                exception.getMessage()
        );

        verify(employeeRepository).existsByEmployeeCode("EMP001");

        verify(departmentRepository, never()).findById(anyLong());
        verify(userRepository, never()).findById(anyLong());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void createEmployee_ShouldThrowException_WhenDepartmentNotFound() {

        when(employeeRepository.existsByEmployeeCode("EMP001"))
                .thenReturn(false);

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> employeeService.createEmployee(request)
                );

        assertEquals(
                "Department not found with id : 1",
                exception.getMessage()
        );

        verify(employeeRepository).existsByEmployeeCode("EMP001");
        verify(departmentRepository).findById(1L);

        verify(userRepository, never()).findById(anyLong());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void createEmployee_ShouldThrowException_WhenUserNotFound() {

        when(employeeRepository.existsByEmployeeCode("EMP001"))
                .thenReturn(false);

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> employeeService.createEmployee(request)
                );

        assertEquals(
                "User not found with id : 1",
                exception.getMessage()
        );

        verify(employeeRepository).existsByEmployeeCode("EMP001");
        verify(departmentRepository).findById(1L);
        verify(userRepository).findById(1L);

        verify(employeeRepository, never()).save(any(Employee.class));
    }
    @Test
    void getEmployeeById_ShouldReturnEmployee() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployeeById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("EMP001", response.getEmployeeCode());

        verify(employeeRepository).findById(1L);
    }

    @Test
    void getEmployeeById_ShouldThrowException_WhenEmployeeNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.getEmployeeById(1L)
        );

        assertEquals(
                "Employee not found with id : 1",
                exception.getMessage()
        );

        verify(employeeRepository).findById(1L);
    }

    @Test
    void getAllEmployees_ShouldReturnEmployeeList() {

        when(employeeRepository.findAll())
                .thenReturn(List.of(employee));

        List<EmployeeResponse> response =
                employeeService.getAllEmployees();

        assertEquals(1, response.size());
        assertEquals("EMP001", response.get(0).getEmployeeCode());

        verify(employeeRepository).findAll();
    }

    @Test
    void getAllEmployees_ShouldReturnEmptyList_WhenNoEmployeesExist() {

        when(employeeRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<EmployeeResponse> response =
                employeeService.getAllEmployees();

        assertTrue(response.isEmpty());

        verify(employeeRepository).findAll();
    }

    @Test
    void updateEmployee_ShouldUpdateEmployeeSuccessfully() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        EmployeeResponse response =
                employeeService.updateEmployee(1L, request);

        assertNotNull(response);
        assertEquals("EMP001", response.getEmployeeCode());

        verify(employeeRepository).findById(1L);
        verify(departmentRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void updateEmployee_ShouldThrowException_WhenEmployeeNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.updateEmployee(1L, request)
        );

        assertEquals(
                "Employee not found with id : 1",
                exception.getMessage()
        );

        verify(employeeRepository).findById(1L);
    }

    @Test
    void updateEmployee_ShouldThrowException_WhenEmployeeCodeAlreadyExists() {

        Employee existingEmployee = Employee.builder()
                .employeeCode("EMP100")
                .department(department)
                .user(user)
                .build();

        existingEmployee.setId(1L);

        request.setEmployeeCode("EMP001");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(employeeRepository.existsByEmployeeCode("EMP001"))
                .thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> employeeService.updateEmployee(1L, request)
        );

        assertEquals(
                "Employee already exists with code : EMP001",
                exception.getMessage()
        );

        verify(employeeRepository).existsByEmployeeCode("EMP001");
    }

    @Test
    void updateEmployee_ShouldThrowException_WhenDepartmentNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.updateEmployee(1L, request)
        );

        assertEquals(
                "Department not found with id : 1",
                exception.getMessage()
        );

        verify(departmentRepository).findById(1L);
    }

    @Test
    void updateEmployee_ShouldThrowException_WhenUserNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.updateEmployee(1L, request)
        );

        assertEquals(
                "User not found with id : 1",
                exception.getMessage()
        );

        verify(userRepository).findById(1L);
    }
    @Test
    void deleteEmployee_ShouldDeleteEmployeeSuccessfully() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        doNothing().when(employeeRepository).delete(employee);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).delete(employee);
    }

    @Test
    void deleteEmployee_ShouldThrowException_WhenEmployeeNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.deleteEmployee(1L)
        );

        assertEquals(
                "Employee not found with id : 1",
                exception.getMessage()
        );

        verify(employeeRepository).findById(1L);
        verify(employeeRepository, never()).delete(any(Employee.class));
    }

    @Test
    void getEmployeeByCode_ShouldReturnEmployee() {

        when(employeeRepository.findByEmployeeCode("EMP001"))
                .thenReturn(Optional.of(employee));

        EmployeeResponse response =
                employeeService.getEmployeeByCode("EMP001");

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("EMP001", response.getEmployeeCode());
        assertEquals("Anil", response.getFirstName());
        assertEquals("Yadav", response.getLastName());
        assertEquals("IT", response.getDepartmentName());
        assertEquals("anil", response.getUsername());

        verify(employeeRepository).findByEmployeeCode("EMP001");
    }

    @Test
    void getEmployeeByCode_ShouldThrowException_WhenEmployeeNotFound() {

        when(employeeRepository.findByEmployeeCode("EMP001"))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.getEmployeeByCode("EMP001")
        );

        assertEquals(
                "Employee not found with code : EMP001",
                exception.getMessage()
        );

        verify(employeeRepository).findByEmployeeCode("EMP001");
    }

}
