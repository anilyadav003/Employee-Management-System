package com.anilyadav.ems.controller;

import com.anilyadav.ems.dto.request.RoleRequest;
import com.anilyadav.ems.dto.response.RoleResponse;
import com.anilyadav.ems.service.auth.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(
            @RequestBody RoleRequest roleRequest) {

        RoleResponse roleResponse = roleService.createRole(roleRequest);

        return new ResponseEntity<>(roleResponse, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {

        RoleResponse roleResponse = roleService.getRoleById(id);

        return ResponseEntity.ok(roleResponse);
    }
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {

        List<RoleResponse> roleResponses = roleService.getAllRoles();

        return ResponseEntity.ok(roleResponses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequest roleRequest) {

        RoleResponse roleResponse = roleService.updateRole(id, roleRequest);

        return ResponseEntity.ok(roleResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {

        roleService.deleteRole(id);

        return ResponseEntity.ok("Role deleted successfully.");
    }

}