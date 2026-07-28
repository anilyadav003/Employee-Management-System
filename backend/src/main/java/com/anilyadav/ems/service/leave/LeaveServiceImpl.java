package com.anilyadav.ems.service.leave;

import com.anilyadav.ems.dto.request.LeaveRequest;
import com.anilyadav.ems.dto.response.LeaveResponse;
import com.anilyadav.ems.entity.employee.Employee;
import com.anilyadav.ems.entity.leave.Leave;
import com.anilyadav.ems.enums.LeaveStatus;
import com.anilyadav.ems.exception.ResourceNotFoundException;
import com.anilyadav.ems.repository.EmployeeRepository;
import com.anilyadav.ems.repository.LeaveRepository;
import com.anilyadav.ems.service.auth.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    private LeaveResponse mapToLeaveResponse(Leave leave) {

        LeaveResponse response = new LeaveResponse();

        response.setId(leave.getId());
        response.setEmployeeId(leave.getEmployee().getId());
        response.setEmployeeCode(leave.getEmployee().getEmployeeCode());
        response.setEmployeeName(
                leave.getEmployee().getFirstName() + " "
                        + leave.getEmployee().getLastName());

        response.setStartDate(leave.getStartDate());
        response.setEndDate(leave.getEndDate());
        response.setReason(leave.getReason());
        response.setStatus(leave.getStatus());

        return response;
    }

    @Override
    public LeaveResponse applyLeave(LeaveRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id : " + request.getEmployeeId()));

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date");
        }

        Leave leave = new Leave();

        leave.setEmployee(employee);
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason());

        // Every new leave request starts as PENDING
        leave.setStatus(LeaveStatus.PENDING);

        Leave savedLeave = leaveRepository.save(leave);

        return mapToLeaveResponse(savedLeave);
    }

    @Override
    public LeaveResponse getLeaveById(Long id) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Leave not found with id : " + id));

        return mapToLeaveResponse(leave);
    }

    @Override
    public List<LeaveResponse> getAllLeaves() {

        return leaveRepository.findAll()
                .stream()
                .map(this::mapToLeaveResponse)
                .toList();
    }

    @Override
    public List<LeaveResponse> getLeavesByEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id : " + employeeId));

        return leaveRepository.findByEmployee(employee)
                .stream()
                .map(this::mapToLeaveResponse)
                .toList();
    }

    @Override
    public List<LeaveResponse> getLeavesByStatus(LeaveStatus status) {

        return leaveRepository.findByStatus(status)
                .stream()
                .map(this::mapToLeaveResponse)
                .toList();
    }

    @Override
    public LeaveResponse updateLeave(Long id, LeaveRequest request) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Leave not found with id : " + id));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id : " + request.getEmployeeId()));

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date");
        }

        leave.setEmployee(employee);
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason());

        Leave updatedLeave = leaveRepository.save(leave);

        return mapToLeaveResponse(updatedLeave);
    }

    @Override
    public LeaveResponse updateLeaveStatus(Long id, LeaveStatus status) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Leave not found with id : " + id));

        leave.setStatus(status);

        Leave updatedLeave = leaveRepository.save(leave);

        return mapToLeaveResponse(updatedLeave);
    }

    @Override
    public void deleteLeave(Long id) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Leave not found with id : " + id));

        leaveRepository.delete(leave);
    }
}