package com.anilyadav.ems.repository;

import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType name);

    boolean existsByName(RoleType name);

}