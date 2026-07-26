package com.anilyadav.ems.service.impl.attendance;

import com.anilyadav.ems.dto.request.AttendanceRequest;
import com.anilyadav.ems.dto.response.AttendanceResponse;
import com.anilyadav.ems.entity.attendance.Attendance;
import com.anilyadav.ems.entity.employee.Employee;
import com.anilyadav.ems.enums.AttendanceStatus;
import com.anilyadav.ems.exception.ResourceAlreadyExistsException;
import com.anilyadav.ems.exception.ResourceNotFoundException;
import com.anilyadav.ems.repository.AttendanceRepository;
import com.anilyadav.ems.repository.EmployeeRepository;
import com.anilyadav.ems.service.auth.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.time.LocalDate;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public AttendanceResponse markAttendance(AttendanceRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id : " + request.getEmployeeId()));

        if (attendanceRepository.existsByEmployeeAndAttendanceDate(
                employee,
                request.getAttendanceDate())) {

            throw new ResourceAlreadyExistsException(
                    "Attendance already marked for employee on "
                            + request.getAttendanceDate());
        }

        Double workingHours = 0.0;

        if (request.getCheckInTime() != null &&
                request.getCheckOutTime() != null) {

            workingHours = Duration.between(
                    request.getCheckInTime(),
                    request.getCheckOutTime()
            ).toMinutes() / 60.0;
        }

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .attendanceDate(request.getAttendanceDate())
                .checkInTime(request.getCheckInTime())
                .checkOutTime(request.getCheckOutTime())
                .status(request.getStatus())
                .workingHours(workingHours)
                .build();

        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        return mapToAttendanceResponse(savedAttendance);
    }

    @Override
    public AttendanceResponse getAttendanceById(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Attendance not found with id : " + id));

        return mapToAttendanceResponse(attendance);
    }

    @Override
    public List<AttendanceResponse> getAllAttendance() {

        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToAttendanceResponse)
                .toList();
    }

    @Override
    public List<AttendanceResponse> getAttendanceByEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id : " + employeeId));

        return attendanceRepository.findByEmployee(employee)
                .stream()
                .map(this::mapToAttendanceResponse)
                .toList();
    }

    @Override
    public List<AttendanceResponse> getAttendanceByDate(LocalDate attendanceDate) {

        return attendanceRepository.findByAttendanceDate(attendanceDate)
                .stream()
                .map(this::mapToAttendanceResponse)
                .toList();
    }
    @Override
    public AttendanceResponse updateAttendance(Long id, AttendanceRequest request) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Attendance not found with id : " + id));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id : " + request.getEmployeeId()));

        if (!attendance.getEmployee().getId().equals(request.getEmployeeId())
                || !attendance.getAttendanceDate().equals(request.getAttendanceDate())) {

            if (attendanceRepository.existsByEmployeeAndAttendanceDate(
                    employee,
                    request.getAttendanceDate())) {

                throw new ResourceAlreadyExistsException(
                        "Attendance already marked for employee on "
                                + request.getAttendanceDate());
            }
        }

        Double workingHours = 0.0;

        if (request.getCheckInTime() != null &&
                request.getCheckOutTime() != null) {

            workingHours = Duration.between(
                    request.getCheckInTime(),
                    request.getCheckOutTime()
            ).toMinutes() / 60.0;
        }

        attendance.setEmployee(employee);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setCheckInTime(request.getCheckInTime());
        attendance.setCheckOutTime(request.getCheckOutTime());
        attendance.setStatus(request.getStatus());
        attendance.setWorkingHours(workingHours);

        Attendance updatedAttendance = attendanceRepository.save(attendance);

        return mapToAttendanceResponse(updatedAttendance);
    }

    @Override
    public void deleteAttendance(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Attendance not found with id : " + id));

        attendanceRepository.delete(attendance);
    }

    private AttendanceResponse mapToAttendanceResponse(Attendance attendance) {

        return AttendanceResponse.builder()
                .id(attendance.getId())
                .employeeId(attendance.getEmployee().getId())
                .employeeCode(attendance.getEmployee().getEmployeeCode())
                .employeeName(
                        attendance.getEmployee().getFirstName() + " "
                                + attendance.getEmployee().getLastName()
                )
                .attendanceDate(attendance.getAttendanceDate())
                .checkInTime(attendance.getCheckInTime())
                .checkOutTime(attendance.getCheckOutTime())
                .status(attendance.getStatus())
                .workingHours(attendance.getWorkingHours())
                .build();
    }
}