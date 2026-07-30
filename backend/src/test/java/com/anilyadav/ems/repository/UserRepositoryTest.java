package com.anilyadav.ems.repository;

import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.entity.auth.User;
import com.anilyadav.ems.enums.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Role createRole() {

        Role role = new Role();
        role.setName(RoleType.ADMIN);
        role.setDescription("Administrator");

        return roleRepository.save(role);
    }

    private User createUser() {

        Role role = createRole();

        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword("password123");
        user.setEnabled(true);
        user.setRole(role);

        return user;
    }

    @Test
    @DisplayName("Should save user successfully")
    void saveUser_ShouldSuccess() {

        User savedUser = userRepository.save(createUser());

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("admin");
    }

    @Test
    @DisplayName("Should find user by id")
    void findById_ShouldReturnUser() {

        User savedUser = userRepository.save(createUser());

        Optional<User> user = userRepository.findById(savedUser.getId());

        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo("admin@gmail.com");
    }

    @Test
    @DisplayName("Should find user by username")
    void findByUsername_ShouldReturnUser() {

        userRepository.save(createUser());

        Optional<User> user = userRepository.findByUsername("admin");

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("admin");
    }

    @Test
    @DisplayName("Should find user by email")
    void findByEmail_ShouldReturnUser() {

        userRepository.save(createUser());

        Optional<User> user = userRepository.findByEmail("admin@gmail.com");

        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo("admin@gmail.com");
    }

    @Test
    @DisplayName("Should return true when username exists")
    void existsByUsername_ShouldReturnTrue() {

        userRepository.save(createUser());

        boolean exists = userRepository.existsByUsername("admin");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when username does not exist")
    void existsByUsername_ShouldReturnFalse() {

        boolean exists = userRepository.existsByUsername("unknown");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should return true when email exists")
    void existsByEmail_ShouldReturnTrue() {

        userRepository.save(createUser());

        boolean exists = userRepository.existsByEmail("admin@gmail.com");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when email does not exist")
    void existsByEmail_ShouldReturnFalse() {

        boolean exists = userRepository.existsByEmail("unknown@gmail.com");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should return all users")
    void findAll_ShouldReturnUsers() {

        userRepository.save(createUser());

        assertThat(userRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("Should delete user successfully")
    void deleteUser_ShouldSuccess() {

        User user = userRepository.save(createUser());

        userRepository.delete(user);

        Optional<User> deletedUser = userRepository.findById(user.getId());

        assertThat(deletedUser).isEmpty();
    }
}