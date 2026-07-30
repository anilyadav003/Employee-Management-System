package com.anilyadav.ems.repository;

import com.anilyadav.ems.entity.attendance.Attendance;
import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.entity.auth.User;
import com.anilyadav.ems.entity.department.Department;
import com.anilyadav.ems.entity.employee.Employee;
import com.anilyadav.ems.enums.AttendanceStatus;
import com.anilyadav.ems.enums.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

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

    private Attendance createAttendance() {

        Employee employee = createEmployee();

        return Attendance.builder()
                .employee(employee)
                .attendanceDate(LocalDate.of(2025, 1, 15))
                .checkInTime(LocalTime.of(9, 0))
                .checkOutTime(LocalTime.of(18, 0))
                .status(AttendanceStatus.PRESENT)
                .workingHours(9.0)
                .build();
    }

    @Test
    @DisplayName("Should save attendance successfully")
    void saveAttendance_ShouldSuccess() {

        Attendance attendance = attendanceRepository.save(createAttendance());

        assertThat(attendance).isNotNull();
        assertThat(attendance.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should find attendance by id")
    void findById_ShouldReturnAttendance() {

        Attendance saved = attendanceRepository.save(createAttendance());

        Optional<Attendance> attendance =
                attendanceRepository.findById(saved.getId());

        assertThat(attendance).isPresent();
    }

    @Test
    @DisplayName("Should find attendance by employee")
    void findByEmployee_ShouldReturnAttendanceList() {

        Employee employee = createEmployee();

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .attendanceDate(LocalDate.of(2025,1,15))
                .checkInTime(LocalTime.of(9,0))
                .checkOutTime(LocalTime.of(18,0))
                .status(AttendanceStatus.PRESENT)
                .workingHours(9.0)
                .build();

        attendanceRepository.save(attendance);

        List<Attendance> attendances =
                attendanceRepository.findByEmployee(employee);

        assertThat(attendances).hasSize(1);
    }

    @Test
    @DisplayName("Should find attendance by date")
    void findByAttendanceDate_ShouldReturnAttendanceList() {

        attendanceRepository.save(createAttendance());

        List<Attendance> attendances =
                attendanceRepository.findByAttendanceDate(
                        LocalDate.of(2025,1,15));

        assertThat(attendances).hasSize(1);
    }

    @Test
    @DisplayName("Should find attendance by employee and date")
    void findByEmployeeAndAttendanceDate_ShouldReturnAttendance() {

        Employee employee = createEmployee();

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .attendanceDate(LocalDate.of(2025,1,15))
                .checkInTime(LocalTime.of(9,0))
                .checkOutTime(LocalTime.of(18,0))
                .status(AttendanceStatus.PRESENT)
                .workingHours(9.0)
                .build();

        attendanceRepository.save(attendance);

        Optional<Attendance> result =
                attendanceRepository.findByEmployeeAndAttendanceDate(
                        employee,
                        LocalDate.of(2025,1,15));

        assertThat(result).isPresent();
    }

    @Test
    @DisplayName("Should return true when attendance exists")
    void existsByEmployeeAndAttendanceDate_ShouldReturnTrue() {

        Employee employee = createEmployee();

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .attendanceDate(LocalDate.of(2025,1,15))
                .checkInTime(LocalTime.of(9,0))
                .checkOutTime(LocalTime.of(18,0))
                .status(AttendanceStatus.PRESENT)
                .workingHours(9.0)
                .build();

        attendanceRepository.save(attendance);

        boolean exists =
                attendanceRepository.existsByEmployeeAndAttendanceDate(
                        employee,
                        LocalDate.of(2025,1,15));

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when attendance does not exist")
    void existsByEmployeeAndAttendanceDate_ShouldReturnFalse() {

        Employee employee = createEmployee();

        boolean exists =
                attendanceRepository.existsByEmployeeAndAttendanceDate(
                        employee,
                        LocalDate.of(2025,1,15));

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should delete attendance successfully")
    void deleteAttendance_ShouldSuccess() {

        Attendance attendance =
                attendanceRepository.save(createAttendance());

        attendanceRepository.delete(attendance);

        assertThat(attendanceRepository.findById(attendance.getId()))
                .isEmpty();
    }
}