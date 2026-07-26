package com.anilyadav.ems.repository;

import com.anilyadav.ems.entity.attendance.Attendance;
import com.anilyadav.ems.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployee(Employee employee);

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    Optional<Attendance> findByEmployeeAndAttendanceDate(
            Employee employee,
            LocalDate attendanceDate
    );

    boolean existsByEmployeeAndAttendanceDate(
            Employee employee,
            LocalDate attendanceDate
    );
}