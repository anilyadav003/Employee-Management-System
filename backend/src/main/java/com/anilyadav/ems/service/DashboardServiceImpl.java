package com.anilyadav.ems.service;

import com.anilyadav.ems.dto.response.DashboardResponse;
import com.anilyadav.ems.service.dashboard.DashboardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Override
    public DashboardResponse getDashboardData() {

        return DashboardResponse.builder()
                .totalEmployees(245L)
                .totalDepartments(12L)
                .attendance(98)
                .leaveRequests(18)
                .employeeGrowth(
                        List.of(
                                80,
                                95,
                                110,
                                135,
                                180,
                                245
                        )
                )
                .departmentDistribution(
                        List.of(
                                DashboardResponse.DepartmentData.builder()
                                        .department("IT")
                                        .count(40)
                                        .build(),

                                DashboardResponse.DepartmentData.builder()
                                        .department("HR")
                                        .count(20)
                                        .build(),

                                DashboardResponse.DepartmentData.builder()
                                        .department("Finance")
                                        .count(15)
                                        .build(),

                                DashboardResponse.DepartmentData.builder()
                                        .department("Marketing")
                                        .count(10)
                                        .build(),

                                DashboardResponse.DepartmentData.builder()
                                        .department("Sales")
                                        .count(15)
                                        .build()
                        )
                )
                .build();
    }
}