package com.anilyadav.ems.service.impl.auth;
import com.anilyadav.ems.exception.ResourceNotFoundException;
import com.anilyadav.ems.dto.request.RoleRequest;
import com.anilyadav.ems.dto.response.RoleResponse;
import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.enums.RoleType;
import com.anilyadav.ems.exception.DuplicateResourceException;
import com.anilyadav.ems.repository.RoleRepository;
import com.anilyadav.ems.service.auth.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {

        if (roleRepository.existsByName(roleRequest.getName())) {
            throw new DuplicateResourceException(
                    "Role already exists with name : " + roleRequest.getName());
        }

        Role role = new Role();
        role.setName(roleRequest.getName());
        role.setDescription(roleRequest.getDescription());

        Role savedRole = roleRepository.save(role);

        return RoleResponse.builder()
                .id(savedRole.getId())
                .name(savedRole.getName())
                .description(savedRole.getDescription())
                .build();
    }

    @Override
    public RoleResponse getRoleById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id : " + id));

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
    @Override
    public RoleResponse getRoleByName(RoleType roleType) {
        return null;
    }

    @Override
    public List<RoleResponse> getAllRoles() {

        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                .map(role -> RoleResponse.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .description(role.getDescription())
                        .build())
                .toList();
    }
    @Override
    public RoleResponse updateRole(Long id, RoleRequest roleRequest) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id : " + id));

        if (!role.getName().equals(roleRequest.getName())
                && roleRepository.existsByName(roleRequest.getName())) {

            throw new DuplicateResourceException(
                    "Role already exists with name : " + roleRequest.getName());
        }

        role.setName(roleRequest.getName());
        role.setDescription(roleRequest.getDescription());

        Role updatedRole = roleRepository.save(role);

        return RoleResponse.builder()
                .id(updatedRole.getId())
                .name(updatedRole.getName())
                .description(updatedRole.getDescription())
                .build();
    }
    @Override

    public void deleteRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id : " + id));

        roleRepository.delete(role);
    }
}