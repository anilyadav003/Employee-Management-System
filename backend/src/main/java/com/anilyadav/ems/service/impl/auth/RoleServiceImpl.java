package com.anilyadav.ems.service.impl.auth;

import com.anilyadav.ems.dto.request.RoleRequest;
import com.anilyadav.ems.dto.response.RoleResponse;
import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.enums.RoleType;
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
        return null;
    }

    @Override
    public RoleResponse getRoleById(Long id) {
        return null;
    }

    @Override
    public RoleResponse getRoleByName(RoleType roleType) {
        return null;
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return null;
    }

    @Override
    public RoleResponse updateRole(Long id, RoleRequest roleRequest) {
        return null;
    }

    @Override
    public void deleteRole(Long id) {

    }
}