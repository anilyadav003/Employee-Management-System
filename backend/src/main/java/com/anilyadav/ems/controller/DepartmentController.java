package com.anilyadav.ems.controller;

import com.anilyadav.ems.dto.request.DepartmentRequest;
import com.anilyadav.ems.dto.response.DepartmentResponse;
import com.anilyadav.ems.service.auth.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse response = departmentService.createDepartment(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {

        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<DepartmentResponse> getDepartmentByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(departmentService.getDepartmentByCode(code));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {

        return ResponseEntity.ok(
                departmentService.updateDepartment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }
}