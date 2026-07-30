package com.anilyadav.ems.repository;

import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.entity.auth.User;
import com.anilyadav.ems.entity.department.Department;
import com.anilyadav.ems.entity.employee.Employee;
import com.anilyadav.ems.entity.leave.Leave;
import com.anilyadav.ems.enums.LeaveStatus;
import com.anilyadav.ems.enums.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LeaveRepositoryTest {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Employee createEmployee() {

        Role role = new Role();
        role.setName(RoleType.EMPLOYEE);
        role.setDescription("Employee");
        role = roleRepository.save(role);

        User user = new User();
        user.setUsername("john");
        user.setEmail("john@gmail.com");
        user.setPassword("password");
        user.setEnabled(true);
        user.setRole(role);
        user = userRepository.save(user);

        Department department = Department.builder()
                .name("Information Technology")
                .code("IT001")
                .description("IT Department")
                .build();
        department = departmentRepository.save(department);

        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .employeeCode("EMP001")
                .dateOfJoining(LocalDate.now())
                .designation("Software Engineer")
                .salary(60000.0)
                .department(department)
                .user(user)
                .build();

        return employeeRepository.save(employee);
    }

    private Leave createLeave() {

        Employee employee = createEmployee();

        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setStartDate(LocalDate.of(2025, 1, 20));
        leave.setEndDate(LocalDate.of(2025, 1, 22));
        leave.setReason("Medical Leave");
        leave.setStatus(LeaveStatus.PENDING);

        return leave;
    }

    @Test
    @DisplayName("Should save leave successfully")
    void saveLeave_ShouldSuccess() {

        Leave savedLeave = leaveRepository.save(createLeave());

        assertThat(savedLeave).isNotNull();
        assertThat(savedLeave.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should find leave by id")
    void findById_ShouldReturnLeave() {

        Leave savedLeave = leaveRepository.save(createLeave());

        assertThat(leaveRepository.findById(savedLeave.getId()))
                .isPresent();
    }

    @Test
    @DisplayName("Should find leave by employee")
    void findByEmployee_ShouldReturnLeaveList() {

        Employee employee = createEmployee();

        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setStartDate(LocalDate.of(2025, 1, 20));
        leave.setEndDate(LocalDate.of(2025, 1, 22));
        leave.setReason("Medical Leave");
        leave.setStatus(LeaveStatus.PENDING);

        leaveRepository.save(leave);

        List<Leave> leaves = leaveRepository.findByEmployee(employee);

        assertThat(leaves).hasSize(1);
    }

    @Test
    @DisplayName("Should find leave by status")
    void findByStatus_ShouldReturnLeaveList() {

        leaveRepository.save(createLeave());

        List<Leave> leaves =
                leaveRepository.findByStatus(LeaveStatus.PENDING);

        assertThat(leaves).hasSize(1);
    }

    @Test
    @DisplayName("Should find leave by employee and status")
    void findByEmployeeAndStatus_ShouldReturnLeaveList() {

        Employee employee = createEmployee();

        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setStartDate(LocalDate.of(2025, 1, 20));
        leave.setEndDate(LocalDate.of(2025, 1, 22));
        leave.setReason("Medical Leave");
        leave.setStatus(LeaveStatus.PENDING);

        leaveRepository.save(leave);

        List<Leave> leaves =
                leaveRepository.findByEmployeeAndStatus(
                        employee,
                        LeaveStatus.PENDING);

        assertThat(leaves).hasSize(1);
    }

    @Test
    @DisplayName("Should return all leaves")
    void findAll_ShouldReturnLeaves() {

        leaveRepository.save(createLeave());

        assertThat(leaveRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("Should delete leave successfully")
    void deleteLeave_ShouldSuccess() {

        Leave leave = leaveRepository.save(createLeave());

        leaveRepository.delete(leave);

        assertThat(leaveRepository.findById(leave.getId()))
                .isEmpty();
    }
}