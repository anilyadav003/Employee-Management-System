package com.anilyadav.ems.repository;

import com.anilyadav.ems.entity.employee.Employee;
import com.anilyadav.ems.entity.leave.Leave;
import com.anilyadav.ems.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployee(Employee employee);

    List<Leave> findByStatus(LeaveStatus status);

    List<Leave> findByEmployeeAndStatus(Employee employee,
                                        LeaveStatus status);
}