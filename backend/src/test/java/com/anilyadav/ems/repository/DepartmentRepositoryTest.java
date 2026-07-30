package com.anilyadav.ems.repository;

import com.anilyadav.ems.entity.department.Department;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department createDepartment() {

        return Department.builder()
                .name("Information Technology")
                .code("IT001")
                .description("IT Department")
                .build();
    }

    @Test
    @DisplayName("Should save department successfully")
    void saveDepartment_ShouldSuccess() {

        Department savedDepartment = departmentRepository.save(createDepartment());

        assertThat(savedDepartment).isNotNull();
        assertThat(savedDepartment.getId()).isNotNull();
        assertThat(savedDepartment.getName()).isEqualTo("Information Technology");
    }

    @Test
    @DisplayName("Should find department by id")
    void findById_ShouldReturnDepartment() {

        Department savedDepartment = departmentRepository.save(createDepartment());

        Optional<Department> department =
                departmentRepository.findById(savedDepartment.getId());

        assertThat(department).isPresent();
        assertThat(department.get().getCode()).isEqualTo("IT001");
    }

    @Test
    @DisplayName("Should find department by name")
    void findByName_ShouldReturnDepartment() {

        departmentRepository.save(createDepartment());

        Optional<Department> department =
                departmentRepository.findByName("Information Technology");

        assertThat(department).isPresent();
        assertThat(department.get().getName())
                .isEqualTo("Information Technology");
    }

    @Test
    @DisplayName("Should find department by code")
    void findByCode_ShouldReturnDepartment() {

        departmentRepository.save(createDepartment());

        Optional<Department> department =
                departmentRepository.findByCode("IT001");

        assertThat(department).isPresent();
        assertThat(department.get().getCode()).isEqualTo("IT001");
    }

    @Test
    @DisplayName("Should return true when department name exists")
    void existsByName_ShouldReturnTrue() {

        departmentRepository.save(createDepartment());

        boolean exists =
                departmentRepository.existsByName("Information Technology");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when department name does not exist")
    void existsByName_ShouldReturnFalse() {

        boolean exists =
                departmentRepository.existsByName("Finance");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should return true when department code exists")
    void existsByCode_ShouldReturnTrue() {

        departmentRepository.save(createDepartment());

        boolean exists =
                departmentRepository.existsByCode("IT001");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when department code does not exist")
    void existsByCode_ShouldReturnFalse() {

        boolean exists =
                departmentRepository.existsByCode("HR001");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should return all departments")
    void findAll_ShouldReturnDepartments() {

        departmentRepository.save(createDepartment());

        assertThat(departmentRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("Should delete department successfully")
    void deleteDepartment_ShouldSuccess() {

        Department department =
                departmentRepository.save(createDepartment());

        departmentRepository.delete(department);

        Optional<Department> deletedDepartment =
                departmentRepository.findById(department.getId());

        assertThat(deletedDepartment).isEmpty();
    }
}