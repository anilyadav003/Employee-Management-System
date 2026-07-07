package com.anilyadav.ems.service.auth;

import com.anilyadav.ems.dto.request.RoleRequest;
import com.anilyadav.ems.dto.response.RoleResponse;
import com.anilyadav.ems.enums.RoleType;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest roleRequest);

    RoleResponse getRoleById(Long id);

    RoleResponse getRoleByName(RoleType roleType);

    List<RoleResponse> getAllRoles();

    RoleResponse updateRole(Long id, RoleRequest roleRequest);

    void deleteRole(Long id);

}