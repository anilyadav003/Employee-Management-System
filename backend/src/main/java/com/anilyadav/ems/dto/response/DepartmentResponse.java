package com.anilyadav.ems.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepartmentResponse {

    private Long id;

    private String name;

    private String code;

    private String description;
}