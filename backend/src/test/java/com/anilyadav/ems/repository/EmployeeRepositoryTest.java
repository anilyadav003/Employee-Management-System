package com.anilyadav.ems.repository;

import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.entity.auth.User;
import com.anilyadav.ems.entity.department.Department;
import com.anilyadav.ems.entity.employee.Employee;
import com.anilyadav.ems.enums.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

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

        return employee;
    }

    @Test
    @DisplayName("Should save employee successfully")
    void saveEmployee_ShouldSuccess() {

        Employee savedEmployee = employeeRepository.save(createEmployee());

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isNotNull();
        assertThat(savedEmployee.getEmployeeCode()).isEqualTo("EMP001");
    }

    @Test
    @DisplayName("Should find employee by id")
    void findById_ShouldReturnEmployee() {

        Employee savedEmployee = employeeRepository.save(createEmployee());

        Optional<Employee> employee =
                employeeRepository.findById(savedEmployee.getId());

        assertThat(employee).isPresent();
        assertThat(employee.get().getFirstName()).isEqualTo("John");
    }

    @Test
    @DisplayName("Should find employee by employee code")
    void findByEmployeeCode_ShouldReturnEmployee() {

        employeeRepository.save(createEmployee());

        Optional<Employee> employee =
                employeeRepository.findByEmployeeCode("EMP001");

        assertThat(employee).isPresent();
        assertThat(employee.get().getEmployeeCode()).isEqualTo("EMP001");
    }

    @Test
    @DisplayName("Should return true when employee code exists")
    void existsByEmployeeCode_ShouldReturnTrue() {

        employeeRepository.save(createEmployee());

        boolean exists =
                employeeRepository.existsByEmployeeCode("EMP001");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when employee code does not exist")
    void existsByEmployeeCode_ShouldReturnFalse() {

        boolean exists =
                employeeRepository.existsByEmployeeCode("EMP999");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should return all employees")
    void findAll_ShouldReturnEmployees() {

        employeeRepository.save(createEmployee());

        assertThat(employeeRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("Should delete employee successfully")
    void deleteEmployee_ShouldSuccess() {

        Employee employee =
                employeeRepository.save(createEmployee());

        employeeRepository.delete(employee);

        Optional<Employee> deletedEmployee =
                employeeRepository.findById(employee.getId());

        assertThat(deletedEmployee).isEmpty();
    }
}