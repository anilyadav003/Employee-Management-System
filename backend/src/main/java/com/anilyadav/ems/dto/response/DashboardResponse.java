package com.anilyadav.ems.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Long totalEmployees;

    private Long totalDepartments;

    private Integer attendance;

    private Integer leaveRequests;

    private List<Integer> employeeGrowth;

    private List<DepartmentData> departmentDistribution;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepartmentData {

        private String department;

        private Integer count;
    }
}