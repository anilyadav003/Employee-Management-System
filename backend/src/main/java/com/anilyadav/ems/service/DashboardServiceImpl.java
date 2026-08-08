package com.anilyadav.ems.service;

import com.anilyadav.ems.dto.response.DashboardResponse;
import com.anilyadav.ems.entity.employee.Employee;
import com.anilyadav.ems.entity.attendance.Attendance;
import com.anilyadav.ems.enums.AttendanceStatus;
import com.anilyadav.ems.enums.LeaveStatus;
import com.anilyadav.ems.repository.AttendanceRepository;
import com.anilyadav.ems.repository.DepartmentRepository;
import com.anilyadav.ems.repository.EmployeeRepository;
import com.anilyadav.ems.repository.LeaveRepository;
import com.anilyadav.ems.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRepository leaveRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboardData() {

        /*
         * ============================================================
         * 1. TOTAL EMPLOYEES
         * ============================================================
         */
        List<Employee> employees = employeeRepository.findAll();

        long totalEmployees = employees.size();


        /*
         * ============================================================
         * 2. TOTAL DEPARTMENTS
         * ============================================================
         */
        long totalDepartments = departmentRepository.count();


        /*
         * ============================================================
         * 3. TODAY'S ATTENDANCE %
         *
         * Present employees / Total employees * 100
         *
         * If there are no employees, attendance = 0.
         * ============================================================
         */
        int attendancePercentage = calculateTodayAttendance(
                totalEmployees
        );


        /*
         * ============================================================
         * 4. PENDING LEAVE REQUESTS
         * ============================================================
         */
        int pendingLeaveRequests =
                leaveRepository.findByStatus(LeaveStatus.PENDING)
                        .size();


        /*
         * ============================================================
         * 5. EMPLOYEE GROWTH
         *
         * Shows cumulative employee count for the last 6 months.
         * ============================================================
         */
        List<Integer> employeeGrowth =
                calculateEmployeeGrowth(employees);


        /*
         * ============================================================
         * 6. DEPARTMENT DISTRIBUTION
         * ============================================================
         */
        List<DashboardResponse.DepartmentData>
                departmentDistribution =
                calculateDepartmentDistribution(employees);


        /*
         * ============================================================
         * BUILD RESPONSE
         * ============================================================
         */
        return DashboardResponse.builder()
                .totalEmployees(totalEmployees)
                .totalDepartments(totalDepartments)
                .attendance(attendancePercentage)
                .leaveRequests(pendingLeaveRequests)
                .employeeGrowth(employeeGrowth)
                .departmentDistribution(departmentDistribution)
                .build();
    }


    /**
     * Calculates today's attendance percentage.
     */
    private int calculateTodayAttendance(long totalEmployees) {

        if (totalEmployees == 0) {
            return 0;
        }

        LocalDate today = LocalDate.now();

        List<Attendance> todayAttendance =
                attendanceRepository.findByAttendanceDate(today);

        long presentEmployees =
                todayAttendance.stream()
                        .filter(attendance ->
                                attendance.getStatus()
                                        == AttendanceStatus.PRESENT)
                        .count();

        return (int) Math.round(
                (presentEmployees * 100.0)
                        / totalEmployees
        );
    }


    /**
     * Calculates cumulative employee growth
     * for the last six months.
     */
    private List<Integer> calculateEmployeeGrowth(
            List<Employee> employees) {

        List<Integer> growth = new ArrayList<>();

        YearMonth currentMonth =
                YearMonth.now();

        for (int i = 5; i >= 0; i--) {

            YearMonth month =
                    currentMonth.minusMonths(i);

            int count =
                    (int) employees.stream()
                            .filter(employee ->
                                    employee.getDateOfJoining() != null
                                            &&
                                            !employee.getDateOfJoining()
                                                    .isAfter(
                                                            month.atEndOfMonth()
                                                    )
                            )
                            .count();

            growth.add(count);
        }

        return growth;
    }


    /**
     * Calculates the number of employees
     * belonging to each department.
     */
    private List<DashboardResponse.DepartmentData>
    calculateDepartmentDistribution(
            List<Employee> employees) {

        Map<String, Long> departmentCounts =
                employees.stream()
                        .filter(employee ->
                                employee.getDepartment() != null)
                        .collect(
                                Collectors.groupingBy(
                                        employee ->
                                                employee
                                                        .getDepartment()
                                                        .getName(),
                                        Collectors.counting()
                                )
                        );

        return departmentCounts.entrySet()
                .stream()
                .map(entry ->
                        DashboardResponse.DepartmentData
                                .builder()
                                .department(entry.getKey())
                                .count(
                                        entry.getValue().intValue()
                                )
                                .build()
                )
                .sorted(
                        (first, second) ->
                                Integer.compare(
                                        second.getCount(),
                                        first.getCount()
                                )
                )
                .toList();
    }
}