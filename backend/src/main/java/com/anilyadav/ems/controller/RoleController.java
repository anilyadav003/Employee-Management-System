package com.anilyadav.ems.controller;

import com.anilyadav.ems.dto.request.RoleRequest;
import com.anilyadav.ems.dto.response.RoleResponse;
import com.anilyadav.ems.service.auth.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}