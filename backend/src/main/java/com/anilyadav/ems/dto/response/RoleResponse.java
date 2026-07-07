package com.anilyadav.ems.dto.response;

import com.anilyadav.ems.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    private Long id;

    private RoleType name;

    private String description;

}