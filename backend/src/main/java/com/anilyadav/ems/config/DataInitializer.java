package com.anilyadav.ems.config;

import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.entity.auth.User;
import com.anilyadav.ems.enums.RoleType;
import com.anilyadav.ems.repository.RoleRepository;
import com.anilyadav.ems.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // =========================
        // Create Roles
        // =========================

        createRoleIfNotExists(
                RoleType.ADMIN,
                "System Administrator"
        );

        createRoleIfNotExists(
                RoleType.HR,
                "Human Resources"
        );

        createRoleIfNotExists(
                RoleType.EMPLOYEE,
                "Employee"
        );

        // =========================
        // Create Default Admin
        // =========================

        createAdminIfNotExists();
    }

    private void createRoleIfNotExists(
            RoleType roleType,
            String description
    ) {

        if (!roleRepository.existsByName(roleType)) {

            Role role = new Role();
            role.setName(roleType);
            role.setDescription(description);

            roleRepository.save(role);

            System.out.println(roleType + " role created.");
        }
    }

    private void createAdminIfNotExists() {

        String username = "anil";
        String email = "anil@ems.com";
        String rawPassword = "password123";

        if (userRepository.existsByUsername(username)) {
            System.out.println("Default admin user already exists.");
            return;
        }

        Role adminRole = roleRepository
                .findByName(RoleType.ADMIN)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "ADMIN role not found."
                        )
                );

        User admin = new User();

        admin.setUsername(username);
        admin.setEmail(email);

        // IMPORTANT:
        // Store BCrypt hash, never the raw password.
        admin.setPassword(
                passwordEncoder.encode(rawPassword)
        );

        admin.setEnabled(true);
        admin.setRole(adminRole);

        userRepository.save(admin);

        System.out.println("======================================");
        System.out.println("Default ADMIN user created.");
        System.out.println("Username: " + username);
        System.out.println("Password: " + rawPassword);
        System.out.println("======================================");
    }
}