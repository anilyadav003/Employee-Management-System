package com.anilyadav.ems.service.auth;

import com.anilyadav.ems.dto.request.LeaveRequest;
import com.anilyadav.ems.dto.response.LeaveResponse;
import com.anilyadav.ems.enums.LeaveStatus;

import java.util.List;

public interface LeaveService {

    LeaveResponse applyLeave(LeaveRequest request);

    LeaveResponse getLeaveById(Long id);

    List<LeaveResponse> getAllLeaves();

    List<LeaveResponse> getLeavesByEmployee(Long employeeId);

    List<LeaveResponse> getLeavesByStatus(LeaveStatus status);

    LeaveResponse updateLeave(Long id, LeaveRequest request);

    LeaveResponse updateLeaveStatus(Long id, LeaveStatus status);

    void deleteLeave(Long id);
}