package com.anilyadav.ems.service.auth;

import com.anilyadav.ems.dto.request.AttendanceRequest;
import com.anilyadav.ems.dto.response.AttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    AttendanceResponse markAttendance(AttendanceRequest request);

    AttendanceResponse getAttendanceById(Long id);

    List<AttendanceResponse> getAllAttendance();

    List<AttendanceResponse> getAttendanceByEmployee(Long employeeId);

    List<AttendanceResponse> getAttendanceByDate(LocalDate attendanceDate);

    AttendanceResponse updateAttendance(Long id, AttendanceRequest request);

    void deleteAttendance(Long id);
}