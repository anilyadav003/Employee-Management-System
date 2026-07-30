package com.anilyadav.ems.repository;

import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.enums.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role createRole(RoleType roleType, String description) {

        Role role = new Role();
        role.setName(roleType);
        role.setDescription(description);

        return role;
    }

    @Test
    @DisplayName("Should save role successfully")
    void saveRole_ShouldSuccess() {

        Role role = createRole(RoleType.ADMIN, "Administrator");

        Role savedRole = roleRepository.save(role);

        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getId()).isNotNull();
        assertThat(savedRole.getName()).isEqualTo(RoleType.ADMIN);
    }

    @Test
    @DisplayName("Should find role by id")
    void findById_ShouldReturnRole() {

        Role savedRole = roleRepository.save(
                createRole(RoleType.HR, "Human Resource"));

        Optional<Role> role = roleRepository.findById(savedRole.getId());

        assertThat(role).isPresent();
        assertThat(role.get().getName()).isEqualTo(RoleType.HR);
    }

    @Test
    @DisplayName("Should find role by name")
    void findByName_ShouldReturnRole() {

        roleRepository.save(
                createRole(RoleType.EMPLOYEE, "Employee"));

        Optional<Role> role = roleRepository.findByName(RoleType.EMPLOYEE);

        assertThat(role).isPresent();
        assertThat(role.get().getDescription()).isEqualTo("Employee");
    }

    @Test
    @DisplayName("Should return true when role exists")
    void existsByName_ShouldReturnTrue() {

        roleRepository.save(
                createRole(RoleType.ADMIN, "Administrator"));

        boolean exists = roleRepository.existsByName(RoleType.ADMIN);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when role does not exist")
    void existsByName_ShouldReturnFalse() {

        boolean exists = roleRepository.existsByName(RoleType.HR);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should return all roles")
    void findAll_ShouldReturnAllRoles() {

        roleRepository.save(createRole(RoleType.ADMIN, "Administrator"));
        roleRepository.save(createRole(RoleType.HR, "Human Resource"));

        assertThat(roleRepository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("Should delete role successfully")
    void deleteRole_ShouldSuccess() {

        Role role = roleRepository.save(
                createRole(RoleType.ADMIN, "Administrator"));

        roleRepository.delete(role);

        Optional<Role> deletedRole = roleRepository.findById(role.getId());

        assertThat(deletedRole).isEmpty();
    }
}