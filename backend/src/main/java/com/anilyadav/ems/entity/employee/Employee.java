package com.anilyadav.ems.entity.employee;

import com.anilyadav.ems.entity.audit.BaseEntity;
import com.anilyadav.ems.entity.auth.User;
import com.anilyadav.ems.entity.department.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 15)
    private String employeeCode;

    @Column(nullable = false)
    private LocalDate dateOfJoining;

    @Column(nullable = false, length = 100)
    private String designation;

    @Column(nullable = false)
    private Double salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}