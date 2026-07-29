package com.anilyadav.ems.service.department;

import com.anilyadav.ems.dto.request.DepartmentRequest;
import com.anilyadav.ems.dto.response.DepartmentResponse;
import com.anilyadav.ems.entity.department.Department;
import com.anilyadav.ems.exception.ResourceAlreadyExistsException;
import com.anilyadav.ems.exception.ResourceNotFoundException;
import com.anilyadav.ems.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Collections;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private DepartmentRequest request;
    private Department department;

    @BeforeEach
    void setUp() {

        request = new DepartmentRequest();
        request.setName("Information Technology");
        request.setCode("IT001");
        request.setDescription("Handles software development");

        department = Department.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .build();

        department.setId(1L);
    }

    @Test
    void createDepartment_ShouldCreateDepartmentSuccessfully() {

        when(departmentRepository.existsByName(request.getName()))
                .thenReturn(false);

        when(departmentRepository.existsByCode(request.getCode()))
                .thenReturn(false);

        when(departmentRepository.save(any(Department.class)))
                .thenReturn(department);

        DepartmentResponse response =
                departmentService.createDepartment(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Information Technology", response.getName());
        assertEquals("IT001", response.getCode());
        assertEquals("Handles software development", response.getDescription());

        verify(departmentRepository).existsByName(request.getName());
        verify(departmentRepository).existsByCode(request.getCode());
        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    void createDepartment_ShouldThrowException_WhenDepartmentNameAlreadyExists() {

        when(departmentRepository.existsByName(request.getName()))
                .thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> departmentService.createDepartment(request)
        );

        assertEquals(
                "Department already exists with name : Information Technology",
                exception.getMessage()
        );

        verify(departmentRepository).existsByName(request.getName());

        verify(departmentRepository, never()).existsByCode(anyString());
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void createDepartment_ShouldThrowException_WhenDepartmentCodeAlreadyExists() {

        when(departmentRepository.existsByName(request.getName()))
                .thenReturn(false);

        when(departmentRepository.existsByCode(request.getCode()))
                .thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> departmentService.createDepartment(request)
        );

        assertEquals(
                "Department already exists with code : IT001",
                exception.getMessage()
        );

        verify(departmentRepository).existsByName(request.getName());
        verify(departmentRepository).existsByCode(request.getCode());

        verify(departmentRepository, never()).save(any(Department.class));

    }
    @Test
    void getDepartmentById_ShouldReturnDepartment() {

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        DepartmentResponse response =
                departmentService.getDepartmentById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Information Technology", response.getName());
        assertEquals("IT001", response.getCode());

        verify(departmentRepository).findById(1L);
    }

    @Test
    void getDepartmentById_ShouldThrowException_WhenDepartmentNotFound() {

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> departmentService.getDepartmentById(1L)
        );

        assertEquals(
                "Department not found with id : 1",
                exception.getMessage()
        );

        verify(departmentRepository).findById(1L);
    }

    @Test
    void getAllDepartments_ShouldReturnDepartmentList() {

        when(departmentRepository.findAll())
                .thenReturn(List.of(department));

        List<DepartmentResponse> response =
                departmentService.getAllDepartments();

        assertEquals(1, response.size());
        assertEquals("Information Technology", response.get(0).getName());
        assertEquals("IT001", response.get(0).getCode());

        verify(departmentRepository).findAll();
    }

    @Test
    void getAllDepartments_ShouldReturnEmptyList_WhenNoDepartmentsExist() {

        when(departmentRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<DepartmentResponse> response =
                departmentService.getAllDepartments();

        assertTrue(response.isEmpty());

        verify(departmentRepository).findAll();
    }

    @Test
    void updateDepartment_ShouldUpdateDepartmentSuccessfully() {

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(departmentRepository.save(any(Department.class)))
                .thenReturn(department);

        DepartmentResponse response =
                departmentService.updateDepartment(1L, request);

        assertNotNull(response);
        assertEquals("Information Technology", response.getName());
        assertEquals("IT001", response.getCode());

        verify(departmentRepository).findById(1L);
        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    void updateDepartment_ShouldThrowException_WhenDepartmentNotFound() {

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> departmentService.updateDepartment(1L, request)
        );

        assertEquals(
                "Department not found with id : 1",
                exception.getMessage()
        );

        verify(departmentRepository).findById(1L);
    }

    @Test
    void updateDepartment_ShouldThrowException_WhenDepartmentNameAlreadyExists() {

        Department existingDepartment = Department.builder()
                .name("HR")
                .code("HR001")
                .description("Human Resources")
                .build();

        existingDepartment.setId(1L);

        request.setName("Information Technology");

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(existingDepartment));

        when(departmentRepository.existsByName(request.getName()))
                .thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> departmentService.updateDepartment(1L, request)
        );

        assertEquals(
                "Department already exists with name : Information Technology",
                exception.getMessage()
        );

        verify(departmentRepository).existsByName(request.getName());
    }

    @Test
    void updateDepartment_ShouldThrowException_WhenDepartmentCodeAlreadyExists() {

        Department existingDepartment = Department.builder()
                .name("Information Technology")
                .code("HR001")
                .description("Human Resources")
                .build();

        existingDepartment.setId(1L);

        request.setCode("IT001");

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(existingDepartment));

        when(departmentRepository.existsByCode(request.getCode()))
                .thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> departmentService.updateDepartment(1L, request)
        );

        assertEquals(
                "Department already exists with code : IT001",
                exception.getMessage()
        );

        verify(departmentRepository).existsByCode(request.getCode());
    }
    @Test
    void deleteDepartment_ShouldDeleteDepartmentSuccessfully() {

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        doNothing().when(departmentRepository).delete(department);

        departmentService.deleteDepartment(1L);

        verify(departmentRepository).findById(1L);
        verify(departmentRepository).delete(department);
    }

    @Test
    void deleteDepartment_ShouldThrowException_WhenDepartmentNotFound() {

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> departmentService.deleteDepartment(1L)
        );

        assertEquals(
                "Department not found with id : 1",
                exception.getMessage()
        );

        verify(departmentRepository).findById(1L);
        verify(departmentRepository, never()).delete(any(Department.class));
    }

    @Test
    void getDepartmentByCode_ShouldReturnDepartment() {

        when(departmentRepository.findByCode("IT001"))
                .thenReturn(Optional.of(department));

        DepartmentResponse response =
                departmentService.getDepartmentByCode("IT001");

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Information Technology", response.getName());
        assertEquals("IT001", response.getCode());
        assertEquals("Handles software development", response.getDescription());

        verify(departmentRepository).findByCode("IT001");
    }

    @Test
    void getDepartmentByCode_ShouldThrowException_WhenDepartmentNotFound() {

        when(departmentRepository.findByCode("IT001"))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> departmentService.getDepartmentByCode("IT001")
        );

        assertEquals(
                "Department not found with code : IT001",
                exception.getMessage()
        );

        verify(departmentRepository).findByCode("IT001");
    }

}
