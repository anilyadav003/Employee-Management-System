package com.anilyadav.ems.config;

import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.enums.RoleType;
import com.anilyadav.ems.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

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
    }

    private void createRoleIfNotExists(RoleType roleType, String description) {

        if (!roleRepository.existsByName(roleType)) {

            Role role = new Role();
            role.setName(roleType);
            role.setDescription(description);

            roleRepository.save(role);

            System.out.println(roleType + " role created.");
        }
    }
}