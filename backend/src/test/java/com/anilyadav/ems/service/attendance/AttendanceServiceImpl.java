package com.anilyadav.ems.service.attendance;

import com.anilyadav.ems.dto.request.AttendanceRequest;
import com.anilyadav.ems.dto.response.AttendanceResponse;
import com.anilyadav.ems.entity.attendance.Attendance;
import com.anilyadav.ems.entity.employee.Employee;
import com.anilyadav.ems.enums.AttendanceStatus;
import com.anilyadav.ems.exception.ResourceAlreadyExistsException;
import com.anilyadav.ems.exception.ResourceNotFoundException;
import com.anilyadav.ems.repository.AttendanceRepository;
import com.anilyadav.ems.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceImplTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    private AttendanceRequest request;
    private Employee employee;
    private Attendance attendance;

    @BeforeEach
    void setUp() {

        request = AttendanceRequest.builder()
                .employeeId(1L)
                .attendanceDate(LocalDate.of(2026, 7, 6))
                .checkInTime(LocalTime.of(9, 0))
                .checkOutTime(LocalTime.of(18, 0))
                .status(AttendanceStatus.PRESENT)
                .build();

        employee = Employee.builder()
                .employeeCode("EMP001")
                .firstName("Anil")
                .lastName("Yadav")
                .build();

        employee.setId(1L);

        attendance = Attendance.builder()
                .employee(employee)
                .attendanceDate(request.getAttendanceDate())
                .checkInTime(request.getCheckInTime())
                .checkOutTime(request.getCheckOutTime())
                .status(request.getStatus())
                .workingHours(9.0)
                .build();

        attendance.setId(1L);
    }

    @Test
    void markAttendance_ShouldCreateAttendanceSuccessfully() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(attendanceRepository.existsByEmployeeAndAttendanceDate(
                employee,
                request.getAttendanceDate()))
                .thenReturn(false);

        when(attendanceRepository.save(any(Attendance.class)))
                .thenReturn(attendance);

        AttendanceResponse response =
                attendanceService.markAttendance(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getEmployeeId());
        assertEquals("EMP001", response.getEmployeeCode());
        assertEquals("Anil Yadav", response.getEmployeeName());
        assertEquals(9.0, response.getWorkingHours());

        verify(employeeRepository).findById(1L);
        verify(attendanceRepository)
                .existsByEmployeeAndAttendanceDate(
                        employee,
                        request.getAttendanceDate());

        verify(attendanceRepository)
                .save(any(Attendance.class));
    }

    @Test
    void markAttendance_ShouldThrowException_WhenEmployeeNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> attendanceService.markAttendance(request)
        );

        assertEquals(
                "Employee not found with id : 1",
                exception.getMessage()
        );

        verify(employeeRepository).findById(1L);

        verify(attendanceRepository, never())
                .existsByEmployeeAndAttendanceDate(any(), any());

        verify(attendanceRepository, never())
                .save(any(Attendance.class));
    }

    @Test
    void markAttendance_ShouldThrowException_WhenAttendanceAlreadyExists() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(attendanceRepository.existsByEmployeeAndAttendanceDate(
                employee,
                request.getAttendanceDate()))
                .thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> attendanceService.markAttendance(request)
        );

        assertEquals(
                "Attendance already marked for employee on 2026-07-06",
                exception.getMessage()
        );

        verify(employeeRepository).findById(1L);

        verify(attendanceRepository)
                .existsByEmployeeAndAttendanceDate(
                        employee,
                        request.getAttendanceDate());

        verify(attendanceRepository, never())
                .save(any(Attendance.class));
    }

    @Test
    void markAttendance_ShouldCreateAttendanceWithZeroWorkingHours_WhenCheckOutTimeIsNull() {

        request.setCheckOutTime(null);

        Attendance attendanceWithoutCheckout = Attendance.builder()
                .employee(employee)
                .attendanceDate(request.getAttendanceDate())
                .checkInTime(request.getCheckInTime())
                .checkOutTime(null)
                .status(request.getStatus())
                .workingHours(0.0)
                .build();

        attendanceWithoutCheckout.setId(2L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(attendanceRepository.existsByEmployeeAndAttendanceDate(
                employee,
                request.getAttendanceDate()))
                .thenReturn(false);

        when(attendanceRepository.save(any(Attendance.class)))
                .thenReturn(attendanceWithoutCheckout);

        AttendanceResponse response =
                attendanceService.markAttendance(request);

        assertNotNull(response);
        assertEquals(0.0, response.getWorkingHours());
        assertNull(response.getCheckOutTime());

        verify(attendanceRepository)
                .save(any(Attendance.class));
    }
    @Test
    void getAttendanceById_ShouldReturnAttendance() {

        when(attendanceRepository.findById(1L))
                .thenReturn(Optional.of(attendance));

        AttendanceResponse response =
                attendanceService.getAttendanceById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getEmployeeId());
        assertEquals("EMP001", response.getEmployeeCode());
        assertEquals("Anil Yadav", response.getEmployeeName());
        assertEquals(LocalDate.of(2026, 7, 6), response.getAttendanceDate());

        verify(attendanceRepository).findById(1L);
    }

    @Test
    void getAttendanceById_ShouldThrowException_WhenAttendanceNotFound() {

        when(attendanceRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> attendanceService.getAttendanceById(1L)
        );

        assertEquals(
                "Attendance not found with id : 1",
                exception.getMessage()
        );

        verify(attendanceRepository).findById(1L);
    }

    @Test
    void getAllAttendance_ShouldReturnAttendanceList() {

        when(attendanceRepository.findAll())
                .thenReturn(List.of(attendance));

        List<AttendanceResponse> response =
                attendanceService.getAllAttendance();

        assertEquals(1, response.size());
        assertEquals("EMP001", response.get(0).getEmployeeCode());
        assertEquals("Anil Yadav", response.get(0).getEmployeeName());

        verify(attendanceRepository).findAll();
    }

    @Test
    void getAllAttendance_ShouldReturnEmptyList_WhenNoAttendanceExists() {

        when(attendanceRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<AttendanceResponse> response =
                attendanceService.getAllAttendance();

        assertTrue(response.isEmpty());

        verify(attendanceRepository).findAll();
    }

    @Test
    void getAttendanceByEmployee_ShouldReturnAttendanceList() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(attendanceRepository.findByEmployee(employee))
                .thenReturn(List.of(attendance));

        List<AttendanceResponse> response =
                attendanceService.getAttendanceByEmployee(1L);

        assertEquals(1, response.size());
        assertEquals("EMP001", response.get(0).getEmployeeCode());
        assertEquals("Anil Yadav", response.get(0).getEmployeeName());

        verify(employeeRepository).findById(1L);
        verify(attendanceRepository).findByEmployee(employee);
    }

    @Test
    void getAttendanceByEmployee_ShouldThrowException_WhenEmployeeNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> attendanceService.getAttendanceByEmployee(1L)
        );

        assertEquals(
                "Employee not found with id : 1",
                exception.getMessage()
        );

        verify(employeeRepository).findById(1L);

        verify(attendanceRepository, never())
                .findByEmployee(any(Employee.class));
    }

    @Test
    void getAttendanceByEmployee_ShouldReturnEmptyList_WhenAttendanceNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(attendanceRepository.findByEmployee(employee))
                .thenReturn(Collections.emptyList());

        List<AttendanceResponse> response =
                attendanceService.getAttendanceByEmployee(1L);

        assertTrue(response.isEmpty());

        verify(employeeRepository).findById(1L);
        verify(attendanceRepository).findByEmployee(employee);
    }

    @Test
    void getAttendanceByDate_ShouldReturnAttendanceList() {

        LocalDate date = LocalDate.of(2026, 7, 6);

        when(attendanceRepository.findByAttendanceDate(date))
                .thenReturn(List.of(attendance));

        List<AttendanceResponse> response =
                attendanceService.getAttendanceByDate(date);

        assertEquals(1, response.size());
        assertEquals(date, response.get(0).getAttendanceDate());

        verify(attendanceRepository).findByAttendanceDate(date);
    }

    @Test
    void getAttendanceByDate_ShouldReturnEmptyList_WhenAttendanceNotFound() {

        LocalDate date = LocalDate.of(2026, 7, 6);

        when(attendanceRepository.findByAttendanceDate(date))
                .thenReturn(Collections.emptyList());

        List<AttendanceResponse> response =
                attendanceService.getAttendanceByDate(date);

        assertTrue(response.isEmpty());

        verify(attendanceRepository).findByAttendanceDate(date);
    }
    @Test
    void updateAttendance_ShouldUpdateAttendanceSuccessfully() {

        when(attendanceRepository.findById(1L))
                .thenReturn(Optional.of(attendance));

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(attendanceRepository.save(any(Attendance.class)))
                .thenReturn(attendance);

        AttendanceResponse response =
                attendanceService.updateAttendance(1L, request);

        assertNotNull(response);
        assertEquals(1L, response.getEmployeeId());
        assertEquals("EMP001", response.getEmployeeCode());
        assertEquals("Anil Yadav", response.getEmployeeName());
        assertEquals(9.0, response.getWorkingHours());

        verify(attendanceRepository).findById(1L);
        verify(employeeRepository).findById(1L);
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    void updateAttendance_ShouldThrowException_WhenAttendanceNotFound() {

        when(attendanceRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> attendanceService.updateAttendance(1L, request)
        );

        assertEquals(
                "Attendance not found with id : 1",
                exception.getMessage()
        );

        verify(attendanceRepository).findById(1L);

        verify(employeeRepository, never()).findById(anyLong());
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    void updateAttendance_ShouldThrowException_WhenEmployeeNotFound() {

        when(attendanceRepository.findById(1L))
                .thenReturn(Optional.of(attendance));

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> attendanceService.updateAttendance(1L, request)
        );

        assertEquals(
                "Employee not found with id : 1",
                exception.getMessage()
        );

        verify(attendanceRepository).findById(1L);
        verify(employeeRepository).findById(1L);

        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    void updateAttendance_ShouldThrowException_WhenAttendanceAlreadyExists() {

        Employee anotherEmployee = Employee.builder()
                .employeeCode("EMP002")
                .firstName("Rahul")
                .lastName("Sharma")
                .build();
        anotherEmployee.setId(2L);

        request.setEmployeeId(2L);

        when(attendanceRepository.findById(1L))
                .thenReturn(Optional.of(attendance));

        when(employeeRepository.findById(2L))
                .thenReturn(Optional.of(anotherEmployee));

        when(attendanceRepository.existsByEmployeeAndAttendanceDate(
                anotherEmployee,
                request.getAttendanceDate()))
                .thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> attendanceService.updateAttendance(1L, request)
        );

        assertEquals(
                "Attendance already marked for employee on 2026-07-06",
                exception.getMessage()
        );

        verify(attendanceRepository)
                .existsByEmployeeAndAttendanceDate(
                        anotherEmployee,
                        request.getAttendanceDate());

        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    void updateAttendance_ShouldUpdateAttendance_WhenEmployeeAndDateAreSame() {

        when(attendanceRepository.findById(1L))
                .thenReturn(Optional.of(attendance));

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(attendanceRepository.save(any(Attendance.class)))
                .thenReturn(attendance);

        AttendanceResponse response =
                attendanceService.updateAttendance(1L, request);

        assertNotNull(response);

        verify(attendanceRepository, never())
                .existsByEmployeeAndAttendanceDate(any(), any());

        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    void updateAttendance_ShouldSetWorkingHoursToZero_WhenCheckOutTimeIsNull() {

        request.setCheckOutTime(null);

        Attendance updatedAttendance = Attendance.builder()
                .employee(employee)
                .attendanceDate(request.getAttendanceDate())
                .checkInTime(request.getCheckInTime())
                .checkOutTime(null)
                .status(request.getStatus())
                .workingHours(0.0)
                .build();

        updatedAttendance.setId(1L);

        when(attendanceRepository.findById(1L))
                .thenReturn(Optional.of(attendance));

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(attendanceRepository.save(any(Attendance.class)))
                .thenReturn(updatedAttendance);

        AttendanceResponse response =
                attendanceService.updateAttendance(1L, request);

        assertEquals(0.0, response.getWorkingHours());
        assertNull(response.getCheckOutTime());

        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    void deleteAttendance_ShouldDeleteAttendanceSuccessfully() {

        when(attendanceRepository.findById(1L))
                .thenReturn(Optional.of(attendance));

        doNothing().when(attendanceRepository).delete(attendance);

        attendanceService.deleteAttendance(1L);

        verify(attendanceRepository).findById(1L);
        verify(attendanceRepository).delete(attendance);
    }

    @Test
    void deleteAttendance_ShouldThrowException_WhenAttendanceNotFound() {

        when(attendanceRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> attendanceService.deleteAttendance(1L)
        );

        assertEquals(
                "Attendance not found with id : 1",
                exception.getMessage()
        );

        verify(attendanceRepository).findById(1L);
        verify(attendanceRepository, never()).delete(any(Attendance.class));
    }

}
