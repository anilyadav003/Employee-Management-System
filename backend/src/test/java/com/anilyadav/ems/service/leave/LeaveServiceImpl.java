package com.anilyadav.ems.service.leave;

import com.anilyadav.ems.dto.request.LeaveRequest;
import com.anilyadav.ems.dto.response.LeaveResponse;
import com.anilyadav.ems.entity.employee.Employee;
import com.anilyadav.ems.entity.leave.Leave;
import com.anilyadav.ems.enums.LeaveStatus;
import com.anilyadav.ems.exception.ResourceNotFoundException;
import com.anilyadav.ems.repository.EmployeeRepository;
import com.anilyadav.ems.repository.LeaveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveServiceImplTest {

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private LeaveServiceImpl leaveService;

    private LeaveRequest request;
    private Employee employee;
    private Leave leave;

    @BeforeEach
    void setUp() {

        request = new LeaveRequest();
        request.setEmployeeId(1L);
        request.setStartDate(LocalDate.of(2026, 7, 10));
        request.setEndDate(LocalDate.of(2026, 7, 15));
        request.setReason("Medical Leave");

        employee = Employee.builder()
                .employeeCode("EMP001")
                .firstName("Anil")
                .lastName("Yadav")
                .build();

        employee.setId(1L);

        leave = new Leave();
        leave.setId(1L);
        leave.setEmployee(employee);
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason());
        leave.setStatus(LeaveStatus.PENDING);
    }

    @Test
    void applyLeave_ShouldCreateLeaveSuccessfully() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(leaveRepository.save(any(Leave.class)))
                .thenReturn(leave);

        LeaveResponse response = leaveService.applyLeave(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getEmployeeId());
        assertEquals("EMP001", response.getEmployeeCode());
        assertEquals("Anil Yadav", response.getEmployeeName());
        assertEquals(LocalDate.of(2026, 7, 10), response.getStartDate());
        assertEquals(LocalDate.of(2026, 7, 15), response.getEndDate());
        assertEquals("Medical Leave", response.getReason());
        assertEquals(LeaveStatus.PENDING, response.getStatus());

        verify(employeeRepository).findById(1L);
        verify(leaveRepository).save(any(Leave.class));
    }

    @Test
    void applyLeave_ShouldThrowException_WhenEmployeeNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> leaveService.applyLeave(request)
        );

        assertEquals(
                "Employee not found with id : 1",
                exception.getMessage()
        );

        verify(employeeRepository).findById(1L);
        verify(leaveRepository, never()).save(any(Leave.class));
    }

    @Test
    void applyLeave_ShouldThrowException_WhenEndDateIsBeforeStartDate() {

        request.setEndDate(LocalDate.of(2026, 7, 5));

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> leaveService.applyLeave(request)
        );

        assertEquals(
                "End date cannot be before start date",
                exception.getMessage()
        );

        verify(employeeRepository).findById(1L);
        verify(leaveRepository, never()).save(any(Leave.class));
    }
    @Test
    void getLeaveById_ShouldReturnLeave() {

        when(leaveRepository.findById(1L))
                .thenReturn(Optional.of(leave));

        LeaveResponse response = leaveService.getLeaveById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getEmployeeId());
        assertEquals("EMP001", response.getEmployeeCode());
        assertEquals("Anil Yadav", response.getEmployeeName());
        assertEquals(LocalDate.of(2026, 7, 10), response.getStartDate());
        assertEquals(LocalDate.of(2026, 7, 15), response.getEndDate());
        assertEquals("Medical Leave", response.getReason());
        assertEquals(LeaveStatus.PENDING, response.getStatus());

        verify(leaveRepository).findById(1L);
    }

    @Test
    void getLeaveById_ShouldThrowException_WhenLeaveNotFound() {

        when(leaveRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> leaveService.getLeaveById(1L)
        );

        assertEquals(
                "Leave not found with id : 1",
                exception.getMessage()
        );

        verify(leaveRepository).findById(1L);
    }

    @Test
    void getAllLeaves_ShouldReturnLeaveList() {

        when(leaveRepository.findAll())
                .thenReturn(List.of(leave));

        List<LeaveResponse> response = leaveService.getAllLeaves();

        assertEquals(1, response.size());
        assertEquals("EMP001", response.get(0).getEmployeeCode());
        assertEquals("Anil Yadav", response.get(0).getEmployeeName());

        verify(leaveRepository).findAll();
    }

    @Test
    void getAllLeaves_ShouldReturnEmptyList_WhenNoLeavesExist() {

        when(leaveRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<LeaveResponse> response = leaveService.getAllLeaves();

        assertTrue(response.isEmpty());

        verify(leaveRepository).findAll();
    }

    @Test
    void getLeavesByEmployee_ShouldReturnLeaveList() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(leaveRepository.findByEmployee(employee))
                .thenReturn(List.of(leave));

        List<LeaveResponse> response =
                leaveService.getLeavesByEmployee(1L);

        assertEquals(1, response.size());
        assertEquals("EMP001", response.get(0).getEmployeeCode());
        assertEquals("Anil Yadav", response.get(0).getEmployeeName());

        verify(employeeRepository).findById(1L);
        verify(leaveRepository).findByEmployee(employee);
    }

    @Test
    void getLeavesByEmployee_ShouldThrowException_WhenEmployeeNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> leaveService.getLeavesByEmployee(1L)
        );

        assertEquals(
                "Employee not found with id : 1",
                exception.getMessage()
        );

        verify(employeeRepository).findById(1L);

        verify(leaveRepository, never())
                .findByEmployee(any(Employee.class));
    }

    @Test
    void getLeavesByEmployee_ShouldReturnEmptyList_WhenNoLeavesExist() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(leaveRepository.findByEmployee(employee))
                .thenReturn(Collections.emptyList());

        List<LeaveResponse> response =
                leaveService.getLeavesByEmployee(1L);

        assertTrue(response.isEmpty());

        verify(employeeRepository).findById(1L);
        verify(leaveRepository).findByEmployee(employee);
    }

    @Test
    void getLeavesByStatus_ShouldReturnLeaveList() {

        when(leaveRepository.findByStatus(LeaveStatus.PENDING))
                .thenReturn(List.of(leave));

        List<LeaveResponse> response =
                leaveService.getLeavesByStatus(LeaveStatus.PENDING);

        assertEquals(1, response.size());
        assertEquals(LeaveStatus.PENDING, response.get(0).getStatus());

        verify(leaveRepository).findByStatus(LeaveStatus.PENDING);
    }

    @Test
    void getLeavesByStatus_ShouldReturnEmptyList_WhenNoLeavesExist() {

        when(leaveRepository.findByStatus(LeaveStatus.PENDING))
                .thenReturn(Collections.emptyList());

        List<LeaveResponse> response =
                leaveService.getLeavesByStatus(LeaveStatus.PENDING);

        assertTrue(response.isEmpty());

        verify(leaveRepository).findByStatus(LeaveStatus.PENDING);
    }
    @Test
    void updateLeave_ShouldUpdateLeaveSuccessfully() {

        when(leaveRepository.findById(1L))
                .thenReturn(Optional.of(leave));

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(leaveRepository.save(any(Leave.class)))
                .thenReturn(leave);

        LeaveResponse response = leaveService.updateLeave(1L, request);

        assertNotNull(response);
        assertEquals(1L, response.getEmployeeId());
        assertEquals("EMP001", response.getEmployeeCode());
        assertEquals("Anil Yadav", response.getEmployeeName());
        assertEquals(LocalDate.of(2026, 7, 10), response.getStartDate());
        assertEquals(LocalDate.of(2026, 7, 15), response.getEndDate());
        assertEquals("Medical Leave", response.getReason());

        verify(leaveRepository).findById(1L);
        verify(employeeRepository).findById(1L);
        verify(leaveRepository).save(any(Leave.class));
    }

    @Test
    void updateLeave_ShouldThrowException_WhenLeaveNotFound() {

        when(leaveRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> leaveService.updateLeave(1L, request)
        );

        assertEquals(
                "Leave not found with id : 1",
                exception.getMessage()
        );

        verify(leaveRepository).findById(1L);

        verify(employeeRepository, never()).findById(anyLong());
        verify(leaveRepository, never()).save(any(Leave.class));
    }

    @Test
    void updateLeave_ShouldThrowException_WhenEmployeeNotFound() {

        when(leaveRepository.findById(1L))
                .thenReturn(Optional.of(leave));

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> leaveService.updateLeave(1L, request)
        );

        assertEquals(
                "Employee not found with id : 1",
                exception.getMessage()
        );

        verify(leaveRepository).findById(1L);
        verify(employeeRepository).findById(1L);

        verify(leaveRepository, never()).save(any(Leave.class));
    }

    @Test
    void updateLeave_ShouldThrowException_WhenEndDateIsBeforeStartDate() {

        request.setEndDate(LocalDate.of(2026, 7, 5));

        when(leaveRepository.findById(1L))
                .thenReturn(Optional.of(leave));

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> leaveService.updateLeave(1L, request)
        );

        assertEquals(
                "End date cannot be before start date",
                exception.getMessage()
        );

        verify(leaveRepository).findById(1L);
        verify(employeeRepository).findById(1L);

        verify(leaveRepository, never()).save(any(Leave.class));
    }

    @Test
    void updateLeaveStatus_ShouldUpdateStatusSuccessfully() {

        leave.setStatus(LeaveStatus.APPROVED);

        when(leaveRepository.findById(1L))
                .thenReturn(Optional.of(leave));

        when(leaveRepository.save(any(Leave.class)))
                .thenReturn(leave);

        LeaveResponse response =
                leaveService.updateLeaveStatus(1L, LeaveStatus.APPROVED);

        assertNotNull(response);
        assertEquals(LeaveStatus.APPROVED, response.getStatus());

        verify(leaveRepository).findById(1L);
        verify(leaveRepository).save(any(Leave.class));
    }

    @Test
    void updateLeaveStatus_ShouldThrowException_WhenLeaveNotFound() {

        when(leaveRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> leaveService.updateLeaveStatus(1L, LeaveStatus.APPROVED)
        );

        assertEquals(
                "Leave not found with id : 1",
                exception.getMessage()
        );

        verify(leaveRepository).findById(1L);

        verify(leaveRepository, never()).save(any(Leave.class));
    }

    @Test
    void deleteLeave_ShouldDeleteLeaveSuccessfully() {

        when(leaveRepository.findById(1L))
                .thenReturn(Optional.of(leave));

        doNothing().when(leaveRepository).delete(leave);

        leaveService.deleteLeave(1L);

        verify(leaveRepository).findById(1L);
        verify(leaveRepository).delete(leave);
    }

    @Test
    void deleteLeave_ShouldThrowException_WhenLeaveNotFound() {

        when(leaveRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> leaveService.deleteLeave(1L)
        );

        assertEquals(
                "Leave not found with id : 1",
                exception.getMessage()
        );

        verify(leaveRepository).findById(1L);
        verify(leaveRepository, never()).delete(any(Leave.class));
    }

}
