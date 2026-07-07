package com.anilyadav.ems.dto.request;

import com.anilyadav.ems.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {

    private RoleType name;

    private String description;

}