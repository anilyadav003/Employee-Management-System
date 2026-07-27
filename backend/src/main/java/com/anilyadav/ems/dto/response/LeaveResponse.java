package com.anilyadav.ems.dto.response;

import com.anilyadav.ems.enums.LeaveStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LeaveResponse {

    private Long id;

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;

    private LeaveStatus status;
}